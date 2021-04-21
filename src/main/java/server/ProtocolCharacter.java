package server;
/**
 * @author sunam
 * @apiNote 客户端发送来的信息必须有特殊的标识——让服务器端可以判断是公聊信息，还是私聊信息。
 * @apiNote 如果是私聊信息，客户端会发送该消息的目的用户（私聊对象）给服务器端，服务器端将该信息发送给该私聊对象。
 */
public interface ProtocolCharacter {
    /**
     * 定义协议字符串的长度
     */
    int  PROTOCOL_LEN=2;
    /**
     * 下面是一些协议字符串，服务器端和客户端交换的信息
     */

    /**
     * 消息
     */
    String MSG_ROUND="§γ";
    /**
     * 用户
     */
    String USER_ROUND="∏∑";
    /**
     * 登录成功
     */
    String LOGIN_SUCCESS="1";
    /**
     * 用户名重复
     */
    String NAME_REP="-1";
    /**
     * 私聊
     */
    String PRIVATE_ROUND="★【";
    /**
     * 分割符号
     */
    String SPLIT_SIGN="※";
}
