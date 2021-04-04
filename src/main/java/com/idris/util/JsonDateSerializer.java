package com.idris.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.idris.error.AppException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * this is used to ensure that all date returned to the user is in this format.
 */
@Component
public class JsonDateSerializer extends JsonSerializer<Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);

    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        try {
            String formattedDate = dateFormat.format(date);
            gen.writeString(formattedDate);
        }
        catch (Exception ex){

            throw new AppException("unable to deserialize date");
        }
    }

}