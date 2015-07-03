package ru.spanferov.library.ws;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.SERVER, faultStringOrReason = "Book with this ID is not found")
public class NonexistentBookIdException extends Exception {

    public NonexistentBookIdException() {
    }

}
