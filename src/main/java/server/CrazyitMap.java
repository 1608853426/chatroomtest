package server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @param <K>
 * @param <V>
 * @author sunam
 * @apiNote Map保存了聊天室所有用户名和对应输出流之间的映射关系
 * @apiNote 服务器端只要获取该用户名对应的输出流即可。
 * @apiNote 服务器端提供了一个HashMap的子类，该类不允许value重复，并提供了根据value获取key，根据value删除key等方法
 */

//扩展HashMap类，CrazyitMap类要求value也不可重复
import java.util.*;

public class CrazyitMap<k,v> {
    //创建一个线程安全的HashMap
    public Map<k,v> map= Collections.synchronizedMap(new HashMap<k,v>());
    //根据value来删除指定项
    public synchronized void removeByValue(Object value){
        for (Object key:map.keySet()){
            if (map.get(key)==value){
                map.remove(key);
                break;
            }
        }
    }
    //获取所有value组成的Set集合
    public synchronized Set<v> valueSet(){
        Set<v> result=new HashSet<v>();
        //将map中的所有value添加到result集合中
        map.forEach((key,value)->result.add(value));
        return result;
    }
    //根据value查找key
    public synchronized k getKeyByValue(v value){
        //遍历所有key组成的集合
        for (k key:map.keySet()){
            //如果指定key对应的value与被搜索的value相同，则返回对应的key
            if(map.get(key)==value||map.get(key).equals(value)){
                return key;
            }
        }
        return null;
    }
    //实现put()方法，该方法不允许value重复
    public synchronized v put(k key,v value){
        //遍历所有value组成的集合
        for (v val:valueSet()){
            //如果某个value与试图放入集合的value相同
            //则抛出一个RuntimeException异常
            if (val.equals(value)&&val.hashCode()==value.hashCode()){
                throw new RuntimeException("MyMap实例不允许有重复的value");
            }
        }
        return map.put(key,value);
    }
}

