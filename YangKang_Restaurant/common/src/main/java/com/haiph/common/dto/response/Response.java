package com.haiph.common.dto.response;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum Response {


  SUCCESS("0000", "Thành Công"),
  ERROR_FORM_AUTH_SERVER("5999", "Lỗi từ máy chủ xác thực [ERR: ADMIN_SYSTEM_ERR]"),
  NOT_FOUND("5501", "Không tìm thấy dữ liệu"),
  ACCESS_DENIED("5403", "Không có quyền truy cập"),

  SYSTEM_ERROR("9999", "Lỗi hệ thống [ERR: HR_SYSTEM_ERR]"),
  NOT_EXITS_REQUIRED("5902", "Yêu cầu không tồn tại"),
  DATA_NOT_FOUND("5505", "Không tìm thấy dữ liệu cần tra cứu"),
  PARAM_INVALID("0998", "Dữ liệu không hợp lệ"),
  MISSING_PARAM("0996", "Thiếu dữ liệu đầu vào bắt buộc"),
  OBJECT_NOT_FOUND("5583", "Đối tượng không tồn tại"),
  OBJECT_IS_EXISTS("5582", "Đối tượng đã tồn tại"),
  LOG_WORK_EXISTS("998", "Đơn log work đã tồn tại"),
  PARAM_NOT_VALID("0006", "Dữ liệu đầu vào không hợp lệ"),
  CAN_NOT_CANCEL_ORDER_STATUS("6001", "Chỉ được thu hồi đơn ở trạng thái chờ duyệt"),
  CAN_NOT_REJECT_ORDER_STATUS("6002", "Chỉ được hủy đơn ở trạng thái chờ duyệt"),
  CAN_NOT_APPROVE_ORDER_STATUS("6003", "Chỉ được chấp nhận đơn ở trạng thái chờ duyệt"),
  PASSWORD_DO_NOT_MATCH("0062", "Hai mật khẩu không giống nhau"),
  PASSWORD_INVALID("0111", "Sai mật khẩu"),
  DEVICE_BORROWED("6004", "Thiết bị đang được mượn không thể xóa");

  private final String responseCode;
  private final String responseMessage;
  private static final Map<String, Response> ERR_CODE_MAP = new HashMap<>();

  static {
    for (Response response : Response.values()) {
      ERR_CODE_MAP.put(response.getResponseCode(), response);
    }
  }

  Response(String responseCode, String responseMessage) {
    this.responseCode = responseCode;
    this.responseMessage = responseMessage;
  }

  public static Response fromCode(String code) {
    Response response = ERR_CODE_MAP.get(code);
    if (response == null) return Response.SYSTEM_ERROR;
    return response;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    stringBuilder.append("\"responseCode\":").append("\"").append(this.responseCode).append("\"").append(",");
    stringBuilder.append("\"responseMessage\":").append("\"").append(this.responseMessage).append("\"");
    stringBuilder.append("}");
    return stringBuilder.toString();
  }
}

class ErrorMsg {

  private ErrorMsg() {
  }

  static final String THE_GHI_NO_KHONG_HOAT_DONG = "Thẻ ghi nợ không hoạt động";
  static final String GIAO_DICH_KHONG_THUC_HIEN_DUOC = "Giao dịch không thực hiện được. Vui lòng thực hiện lại sau.";
}
