package com.olymtech.nebula.entity.enums;

/**
 * Created by wangyiqiang on 16/3/11.
 */
public enum LogAction {
    APPLY_PUBLISH_EVENT("申请发布"),
    PUBLISH_APPROVE("发布审批"),
    PUBLISH_PREPARATION("发布准备"),
    START_PRE_PUBLISH("启动预发布"),
    START_FORMAL_PUBLISH("正式发布"),
    CONFIRM_SUCCESS("确认成功"),
    ROLL_BACK("我要回滚"),
    RE_PUBLISH("重新发布"),
    ENTER_NEXT_PUBLISH("升级发布"),
    FINISH_ETC_EDIT("完成配置编辑"),
    CONFIGURATION_APPROVAL("配置审批");

    private String description;

    private LogAction(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
