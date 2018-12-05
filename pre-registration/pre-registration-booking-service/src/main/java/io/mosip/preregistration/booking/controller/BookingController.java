package io.mosip.preregistration.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.mosip.kernel.core.exception.ParseException;
import io.mosip.preregistration.booking.dto.AvailabilityDto;
import io.mosip.preregistration.booking.dto.BookingDTO;
import io.mosip.preregistration.booking.dto.ResponseDto;
import io.mosip.preregistration.booking.service.BookingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Data Sync Controller
 * 
 * @author M1037717
 *
 */
@RestController
@RequestMapping("/v0.1/pre-registration/book/")
@Api(tags = "Booking")
@CrossOrigin("*")
public class BookingController {

	@Autowired
	private BookingService bookingService;

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@GetMapping(path = "/masterSync", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Sync master Data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Master Data Sync is successful"),
			@ApiResponse(code = 400, message = "Unable to fetch the records") })
	public ResponseEntity<ResponseDto<String>> saveAvailability() {
		ResponseDto<String> response = bookingService.addAvailability();
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@SuppressWarnings("rawtypes")
	@GetMapping(path = "/availability", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Fetch availability Data")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Availablity details fetched successfully"),
			@ApiResponse(code = 400, message = "Unable to fetch the records") })
	public ResponseEntity<ResponseDto<AvailabilityDto>> getAvailability(
			@RequestParam(value = "RegCenterId") String regID) {

		ResponseDto<AvailabilityDto> response = bookingService.getAvailability(regID);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	/**
	 * @param bookingDTO
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@PostMapping(path = "/book")
	public ResponseEntity<ResponseDto<BookingDTO>> book(@RequestBody(required = true) BookingDTO bookingDTO)
			throws ParseException, java.text.ParseException {

		ResponseDto<BookingDTO> responseDTO = bookingService.bookAppointment(bookingDTO);
		return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

	}

}
