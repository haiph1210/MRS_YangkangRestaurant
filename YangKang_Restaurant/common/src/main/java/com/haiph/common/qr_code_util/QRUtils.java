package com.haiph.common.qr_code_util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.haiph.common.dto.response.Response;
import com.haiph.common.exception.CommonException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class QRUtils {
    public static String gennaterateQrCode(String data, Integer width, Integer height) {
        StringBuilder result = new StringBuilder();
        if (!data.isEmpty()) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                QRCodeWriter writer = new QRCodeWriter();
                BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, width, height);
                BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
                result.append("data:image/png;base64,");
                result.append(new String(Base64.getEncoder().encode(byteArrayOutputStream.toByteArray())));
            } catch (IOException e) {
                throw new CommonException(Response.DATA_NOT_FOUND, "Cannot enCode");
            } catch (WriterException e) {
                throw new CommonException(Response.DATA_NOT_FOUND, "Cannot Write to PNG");
            }
        }
        return result.toString();
    }

    public static String prettyObject(Object object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String jsonString = mapper.writeValueAsString(object);
            return jsonString;
//            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
