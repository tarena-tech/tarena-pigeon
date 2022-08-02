package com.tarena.mnmp.admin.codegen.api.app;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel("app审核模型")
public class AppAuditParam {

    @NotNull(message = "id不能为空")
    @Min(1)
    private Long id;

    @Min(value = -1, message = "非法参数")
    @Max(value = 1, message = "非法参数")
    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;

    @NotBlank(message = "审核意见不能为空")
    private String auditResult;

}
