package com.fantasy.framework.util.jackson.serializer;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.fantasy.framework.util.common.StringUtil;

public class TextSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(StringUtil.escapeHtml(value));
	}

}
