package server;
/**
 * @author sunam
 * @apiNote 客户端发送来的信息必须有特殊的标识——让服务器端可以判断是公聊信息，还是私聊信息。
 * @apiNote 如果是私聊信息，客户端会发送该消息的目的用户（私聊对象）给服务器端，服务器端如何将该信息发送给该私聊对象。
 */
public interface CrazyitProtocol {
    //定义协议字符串的长度
    int  PROTOCOL_LEN=2;
    //下面是一些协议字符串，服务器端和客户端交换的信息
    String MSG_ROUND="§γ";
    String USER_ROUND="∏∑";
    String LOGIN_SUCCESS="1";
    String NAME_REP="-1";
    String PRIVATE_ROUND="★【";
    String SPLIT_SIGN="※";
}
