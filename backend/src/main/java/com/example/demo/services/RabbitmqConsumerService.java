package com.example.demo.services;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class RabbitmqConsumerService {

    @Autowired
    MinioService minioService;

    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String fileBody) {
        System.out.println("Message " + fileBody);

        try{
           byte[] image = minioService.getOriginalFile(fileBody); 
           
           BufferedImage bufferedImage = toBufferedImage(image);

           BufferedImage resizedBufferedImage = resizeImage(bufferedImage, 500, 500);
           
           byte[] resizedImage = toByteArray(resizedBufferedImage, "jpeg");

           minioService.uploadResizedFile(resizedImage, fileBody);


        } catch(IOException e){
            throw new RuntimeException(e);  
        } catch(Exception e){
            throw new RuntimeException(e);  
        }
    }    

    public static BufferedImage toBufferedImage(byte[] bytes) throws IOException{        
        InputStream inputStream = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        return bufferedImage;
    }

    public static byte[] toByteArray(BufferedImage bufferedImage,  String format) throws IOException{
        try(ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            ImageIO.write(bufferedImage, format, baos);
            byte[] bytes = baos.toByteArray();
        
            return bytes;
        }
        
    }

    
    public static BufferedImage resizeImage(BufferedImage originalImage, 
                                            int targetWidth, int targetHeight) 
                                            throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage)
            .size(targetWidth, targetHeight)
            .outputFormat("JPEG")
            .outputQuality(1)
            .toOutputStream(outputStream);
        
        byte[] data = outputStream.toByteArray();
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            return ImageIO.read(inputStream);
        }
    }

}
