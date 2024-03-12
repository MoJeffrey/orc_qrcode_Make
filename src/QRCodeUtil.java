import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.common.BitMatrix;
/**
 * @ClassName QRCodeUtilTest
 * @Description TODO
 * @Author lougang
 * @Date 2024-02-23 8:44
 * @Version 1.0
 */
public class QRCodeUtil {

    public static List<String> generateQRCodesWithBackgroundColor(List<String> textList, int width, int height, String filePath, List<String> fileNameList,List<String> backgroundColorList) throws WriterException, IOException {
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < textList.size(); i++) {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 0);

            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.QR_VERSION, 15);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(textList.get(i), BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int backgroundColor;
            if(backgroundColorList.isEmpty()){
                backgroundColor = 0xFF000000;
            }else {
                long longValue = Long.parseLong(backgroundColorList.get(i % backgroundColorList.size()).substring(2) ,16);
                backgroundColor = (int) longValue;
            }
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int color = bitMatrix.get(x, y) ? backgroundColor : 0xFFFFFFFF;
                    image.setRGB(x, y, color);
                }
            }
            // 输出二维码到指定文件
            String imagePath = filePath + fileNameList.get(i) + ".jpg";
            File outputFile = new File(imagePath);
            try {
                ImageIO.write(image, "jpg", outputFile);
                urlList.add(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return urlList;
    }
}
