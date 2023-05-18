package com.haiph.common.qr_code_util;

import org.springframework.stereotype.Service;

@Service
public class QRService {
    private static final Integer QR_CODE_SIZE_WIDTH = 300;
    private static final Integer QR_CODE_SIZE_HEIGHT = 300;
    public String gennateQrCode(Object o) {
        String loadData = QRUtils.prettyObject(o);
        String qrCode = QRUtils.gennaterateQrCode(loadData,QR_CODE_SIZE_WIDTH,QR_CODE_SIZE_HEIGHT);
        return qrCode;
    }
}
