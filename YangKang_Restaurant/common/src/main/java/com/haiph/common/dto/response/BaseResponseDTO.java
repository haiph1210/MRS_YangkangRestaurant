package com.haiph.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponseDTO {

  @Schema(description = "Mã giao dịch gửi sang ngân hàng")
  private String bankAuditNumber;

  @Schema(description = "Mã giao dịch nhận về từ ngân hàng")
  private String bankTraceNumber;

  @Schema(description = "Mã kết quả thực hiện yêu cầu")
  private String bankResponseCode;

  @Schema(description = "Mô tả kết quả từ ngân hàng")
  private String bankResponseDesc;

  @Schema(description = "Thời gian gửi request sang bank")
  private Date bankRequestTime;

  @Schema(description = "Thời gian nhận response từ bank")
  private Date bankResponseTime;

  @Schema(description = "Mã giao dịch do MS sinh")
  private String traceNumber;
}
