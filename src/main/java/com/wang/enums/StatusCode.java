package com.wang.enums;

/**
 * @ClassName StatusCode
 * @Description: 通用的响应状态码enum
 * @Author lidong Han
 * @Date 2019/11/2 11:06
 * @Version 1.0
 **/
@SuppressWarnings("all")
public enum StatusCode {

    Success(200, "成功"),
    Fail(500, "失败"),
    INVALID_PARAM(-2, "文件错误,请联系管理员"),


    FailFate(-100, "请求轮次不存在"),
    InvalidParams(201, "非法的参数!"),
    UserNotLogin(202, "用户没登录"),
    NotUTF8AndDOM(204, "非UTF-8无BOM格式!"),
    ExistLog(205, "存在同名.log校验文件!"),
    NotLikeName(206, "log文件中文件名与报送数据文件名称不同"),
    NotRealitySize(207, "文件大小（字节）与数据文件实际大小不相符"),
    NotCounter(208, "检查数据文件实际行数与.log文件记录数（行数）不符合"),
    SeparatorError(209, "数据项的分隔符是不正确"),
    NotFonud(210, "检查数据未加载！"),
    NotFountJGDM(211, "机构代码是不正确！"),
    NotFountTabelNames(212, "表名对应字符串是不正确！"),
    DataDoesNotExist(-213, "只有草稿状态下的规则才能被测试"),
    DataDoesNotExists(-214, "所选择的规则当中含有不是草稿状态下的"),


    /**
     * 没有权限
     */
    UNAUTHORIZED(401,  "Unauthorized"),
    /**
     * 登录踢出
     */
    LOGIN_WAS_KICKED_OUT(505,  "您的账号在另一地点登录,您被强制下线"),

    USER_ACCOUNT_DISABLE(2005,"账号不可用"),
    USER_ACCOUNT_NOT_EXIST(2007,"账号不存在"),

    USER_ACCOUNT_ALREADY_EXIST(2008,"账号已存在"),

    TOKEN_EXPIRED(505,  "token过期，请重新登录"),
    UnknownError(500, "未知异常，请联系管理员!"),
    InvalidCode(501, "验证码不正确!"),
    AccountPasswordNotMatch(502, "账号密码不匹配!"),
    AccountHasBeenLocked(503, "账号已被锁定,请联系管理员!"),
    AccountValidateFail(504, "账户验证失败!"),
    CurrUserHasNotPermission(505, "当前用户没有权限访问该资源或者操作！"),
    WrongAccountOrPassword(506, "用户名或者密码错误!"),
    AccountAlreadyExists(507, "账户已存在!"),
    ThePasswordsEnteredAreInconsistent(508, "输入的密码不一致!"),
    AccountAlreadyNoxistsd(509, "账号已被锁定!"),
    AccountAlreadyNoxists(510, "账号不正确!"),
    HasBeenBound(511, "选择的角色已经被绑定用户不允许删除"),
    ThereAreSubordinateResourcesInTheChangedResources(512, "该资源存在下级资源，无法删除！"),
    TheRoleNameHasBeenSaved(513, "该角色名已存在请重新输入"),
    OrganizationAlreadyExists(514, "机构代码已存在请重新输入"),
    DeptAlreadyExists(515, "部门已存在请重新输入"),
    SupervisorAssigned(516, "监管员已分配请重新选择"),
    ExecutorAssigned(517, "执行人已分配请重新选择"),
    ChoiceDept(518, "请给用户分配部门"),
    ChoiceRole(519, "请给用户选择角色"),
    OrganizationAbbreviationAlreadyExists(525, "银行机构简称已存在，请重新输入"),
    JrxkzhAlreadyExists(526, "金融许可证号已存在请重新输入"),

    SkipRuleCheck(527, "查询到已存在检核结果，请问是否需要跳过,直接计算指标？"),
    DataIncomplete(528, "指标的计算结果可能有如下原因导致不准确,请确认是否继续？"),
    IndexCalculationFail(529,"指标计算失败"),
    IndexDataCheckFail(529,"数据检核失败"),

    PasswordCanNotBlank(1000, "密码不能为空!"),
    OldPasswordNotMatch(1001, "用户名或者密码错误!"),
    UpdatePasswordFail(1002, "修改密码失败~请联系管理员!"),
    SysUserCanNotBeDelete(1003, "超级系统管理员不能被删除!"),
    CurrUserCanNotBeDelete(1004, "当前用户不能删除自己!"),
    Ti(1005, "提供的旧密码错误，请重新输入"),
    PassWordUpdSuccess(1006, "密码修改成功"),
    TokenIsNot(1007, "token不存在"),

    CurrentPermissionAssignedColorCorrection(1008, "当前权限已分配角色请重新选择"),

    DeptHasSubDeptCanNotBeDelete(2001, "该部门下包含有子部门，请先删除子部门!"),

    SpecificMenuCanNotBeDelete(3001, "指定的菜单不能被删除，要删除就手动数据库删除~fuck you!"),

    PostCodeHasExist(4001, "岗位编码已存在!"),

    MenuHasSubMenuListCanNotDelete(5001, "该菜单下有子菜单，请先删除子菜单！"),

    SysUserAndCurrUserCanNotResetPsd(6001, "超级系统管理员与自己不能重置密码！"),


    UserNamePasswordNotBlank(50000, "账户密码不能为空!"),
    AccessTokenNotBlank(50001, "accessToken必填，请在请求头header中塞入该字段"),

    TokenValidateExpireToken(60001, "Token已过期"),
    TokenValidateCheckFail(60002, "Token验证失败"),

    AccessTokenNotExist(70001, "Token不存在-请重新登录!"),
    AccessTokenInvalidate(70002, "无效的Token!"),
    UserDoesnotHavePermission(70003, "当前登陆的用户没有权限!"),

    AccessTokenNotExistRedis(80001, "Token不存在或已经过期-请重新登录!"),

    AccessSessionNotExist(90001, "用户没登录或登录Session已经过期-请重新登录!"),

    LoginFail(100000, "登录失败！"),
    //    CurrUserHasNotPermission(100001,"当前用户没有权限访问该资源或者操作！"),
    CurrUserNotLogin(100002, "当前用户没有登录，请先进行登录！"),
    CurrUserNotLogins(100003, "无权访问(Unauthorized):"),


    DuplicateKey(101000, "数据重复提交"),

    RuleBlankField(101000, "空白字段"),

    ;

    private Integer code;
    private String message;

    StatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }
}
