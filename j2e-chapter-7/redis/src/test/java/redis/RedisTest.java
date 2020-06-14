package redis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
	@Test
	public void test1() {
		//����redis����localhostΪ����	
		Jedis jedis = new Jedis("localhost");
		//����auth������֤
		jedis.auth("123456");
		//�������ӣ����������᷵��pong�ַ���
		String ping = jedis.ping();
		System.out.println(ping);
		//�ر����ӣ���JDBCһ���������Ƿǳ�������Դ�в����Զ��رգ���Ҫ����close�����ӹر�
		jedis.close();
	}
	@Test
	public void test2() {
		//����Jedis���ӳ�
		JedisPool pool = new JedisPool("localhost");
		//ͨ�����ӳػ�ȡJedis����
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		String ping = jedis.ping();
		System.out.println(ping);
		//�������ӳ�
		pool.destroy();
	}
	@Test
	public void test3() {
		JedisPoolConfig config = new JedisPoolConfig();
		//�������񵽴��û�п��е�����ʱ�Ƿ���������ֱ����ʱ��Ĭ��ֵΪtrue������false���ֱ���׳��쳣��ֹ������ύ
		config.setBlockWhenExhausted(false);
		//����Pool����������������Ĭ��ֵΪ8���������������������趨ֵ��Pool������ٶ��������
		config.setMaxIdle(5);
		//����Pool�������������Ĭ��ֵΪ8
		config.setMaxTotal(10);
		//���������������ȴ�ʱ��(����)����setBlockWhenExhausted��ֵΪfalseʱ��������Ч
		config.setMaxWaitMillis(5000);
		//������С������������Ĭ��Ϊ0
		config.setMinIdle(2);
		//��ȡJedis����ʱ���������Ƿ���Ч��Ĭ�ϲ����
		config.setTestOnBorrow(true);
		JedisPool pool = new JedisPool("localhost");
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		String ping = jedis.ping();
		System.out.println(ping);
		pool.destroy();
	}
	private static JedisPool pool;
	private static Jedis getJedis() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setBlockWhenExhausted(false);
		config.setMaxIdle(5);
		config.setMaxTotal(10);
		config.setMaxWaitMillis(5000);
		config.setMinIdle(2);
		config.setTestOnBorrow(true);
		pool = new JedisPool("localhost");
		Jedis jedis = pool.getResource();
		jedis.auth("123456");
		return jedis;
	}
	@Test
	public void test() throws InterruptedException {
		Jedis jedis = getJedis();
		//���Redis�е����м�ֵ�ԣ�����ʱ����ʹ��
		jedis.flushDB();
        System.out.println("������ֵ��~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        //������ֵ�ԣ����óɹ�����ok
        System.out.println(jedis.set("k1","value1"));
        System.out.println(jedis.set("k2","value2"));
        System.out.println(jedis.set("k3", "value3"));
        //ɾ���ɹ�����ok��ɾ��ʧ��(����û�и�key)�򷵻�0
        System.out.println("ɾ��k2:"+jedis.del("k2"));
        //��ȡkey��ֵ��key�������򷵻�null
        System.out.println("��ȡk2��ֵ:"+jedis.get("k2"));
        //��key��value���׷�ӣ����key����������½�һ��key
        System.out.println("׷���ַ�����"+jedis.append("k3", "End"));
        System.out.println("k3��ֵ��"+jedis.get("k3"));
        //mset,���óɹ�����OK
        System.out.println("ͬʱ���ö����ֵ�ԣ�"+jedis.mset("k1","v1","k2","v2","k3","v3"));
        //mget,���ַ����������ʽ���أ�key�����ڻ᷵��null
        System.out.println("��ȡ�����ֵ�Ե�ֵ��"+jedis.mget("k1","k2","k3"));
        System.out.println("ͬʱ��ȡ���key��ֵ��"+jedis.mget("k1","k2","k3","k4"));
        System.out.println("ͬʱɾ�����key��ֵ��"+jedis.del(new String[]{"k1","k2"}));
        System.out.println("ͬʱ��ȡ���key��ֵ��"+jedis.mget("key01","key02","key03"));
        jedis.flushDB();
        //setnx,��key�����ڵ�����£���ֵ�Ż�ɹ�
        System.out.println(jedis.setnx("k1", "value1"));
        System.out.println(jedis.setnx("k2", "value2"));
        System.out.println(jedis.setnx("k2", "value2-new"));
        System.out.println(jedis.get("k1"));
        System.out.println(jedis.get("k2"));
        //����key-value��ֵ�ԣ���������Чʱ��
        System.out.println(jedis.setex("k3", 2, "value3"));
        System.out.println(jedis.get("k3"));
        //
        TimeUnit.SECONDS.sleep(3);
        System.out.println(jedis.get("k3"));
        //��ȡkey��ֵ��Ϊ�������µ�ֵ
        System.out.println(jedis.getSet("k2", "k2GetSet"));
        System.out.println(jedis.get("k2"));
        //getrange,��ȡ�±�2-4���Ӵ�������2������4���±��0��ʼ��length-1���ܣ�
        System.out.println("��ȡk2��ֵ���Ӵ���"+jedis.getrange("key2", 2, 4));
	}
	@After
	public void after() {
		pool.destroy();
	}
	@Test
	public void test4() {
		Jedis jedis = getJedis();
	    jedis.flushDB();
	    //���б����Ԫ�أ�֧��һ�����߶��
	    jedis.lpush("myList", "List", "Set", "Map", "Tree");
	    jedis.lpush("myList", "ArrayList");
	    //��ȡָ�������Ԫ�أ�ͬʱҲ֧�ִӺ���ǰ���±꣬�����-1���ʾ������һ��Ԫ�أ�-2��ʾ�����ĵڶ���Ԫ�أ�����
	    System.out.println("myList��ȫ��Ԫ�أ�"+jedis.lrange("myList", 0, -1));
	    System.out.println("ָ��myList����1-2��Ԫ�أ�"+jedis.lrange("myList",1,2));
	    
	    // ���б�ָ����ֵɾ�� ���ڶ�������Ϊָ�������ĸ���(list�����ǿ��ظ����б�)��������ѭ���Ƚ����ԭ�򡮣��󱣴���������Ȼᱻɾ����
	    System.out.println("��myList��ָ��Ԫ��ɾ����"+jedis.lrem("myList", 2, "HashMap"));
	    System.out.println("myList��ȫ��Ԫ�أ�"+jedis.lrange("myList", 0, -1));
	    System.out.println("ɾ��ָ�����������Ԫ�أ�"+jedis.ltrim("myList", 0, 3));
	    System.out.println("myList��ȫ��Ԫ�أ�"+jedis.lrange("myList", 0, -1));
	    //��ջ��������ȡ������˵�Ԫ�أ�������Ԫ���Ƴ�
	    System.out.println("myList�б��ջ��"+jedis.lpop("myList"));
	    System.out.println("myList��ȫ��Ԫ�أ�"+jedis.lrange("myList", 0, -1));
	    //��list��������Ԫ�أ�����List����Ԫ�صĸ���(���֮��)
	    System.out.println("��myList���Ԫ�أ�"+jedis.rpush("myList", "EnumMap"));
	    System.out.println("myList��ȫ��Ԫ�أ�"+jedis.lrange("myList", 0, -1));
	   //ȡ���б����Ҷ˵�Ԫ�أ�������Ԫ���Ƴ�
	    System.out.println("myList�б��ջ���Ҷˣ���"+jedis.rpop("myList"));
	    System.out.println("myList��ȫ��Ԫ�أ�"+jedis.lrange("myList", 0, -1));
	    System.out.println("�޸�myListָ���±�1�����ݣ�"+jedis.lset("myList", 1, "LinkedList"));
	    System.out.println("myList��ȫ��Ԫ�أ�"+jedis.lrange("myList", 0, -1));
	    
	    System.out.println("myList��Ԫ�ظ�����"+jedis.llen("myList"));
	    System.out.println("��ȡmyListָ���±�Ϊ2��Ԫ�����ݣ�"+jedis.lindex("myList", 2));
	    
	    jedis.lpush("sorted", "3","6","2","0","7","4");
	    System.out.println("sorted����ǰ��"+jedis.lrange("sorted", 0, -1));
	    //Ϊlist�������򣬷������������ݣ��������򲢲��ܸı�ԭlist������
	    //����Ƿ�number��������ᰴ��ASCII���������
	    System.out.println(jedis.sort("sorted"));
	    System.out.println("sorted�����"+jedis.lrange("sorted", 0, -1));
	}
	@Test
	public void test5() {
		Jedis jedis = getJedis();
		jedis.flushDB();
        //������ӳɹ�Ԫ�صĸ���
        System.out.println(jedis.sadd("mySet", "s1","s2","s4","s3","s0","s8","s7","s5"));
        //Set�����ظ�����ͬԪ�ز�����ӳɹ�
        System.out.println(jedis.sadd("mySet", "s6"));
        System.out.println(jedis.sadd("mySet", "s6"));
        System.out.println("mySet������Ԫ�أ�"+jedis.smembers("mySet"));
        //ɾ���ɹ�����1��ʧ�ܷ���0
        System.out.println("ɾ��һ��Ԫ��s0��"+jedis.srem("mySet", "s0"));
        System.out.println("mySet������Ԫ�أ�"+jedis.smembers("mySet"));
        //����ɾ���ɹ���Ԫ�ظ���
        System.out.println("ɾ������Ԫ��s7��s6��"+jedis.srem("mySet", "s7","s6"));
        System.out.println("mySet������Ԫ�أ�"+jedis.smembers("mySet"));
        //���ر��Ƴ���Ԫ��
        System.out.println("������Ƴ������е�һ��Ԫ�أ�"+jedis.spop("mySet"));
        System.out.println("mySet������Ԫ�أ�"+jedis.smembers("mySet"));
        System.out.println("mySet�а���Ԫ�صĸ�����"+jedis.scard("mySet"));
        //�ж�set�Ƿ����ĳԪ�أ�����boolean
        System.out.println("�ж�mySet���Ƿ���Ԫ��s3��"+jedis.sismember("mySet", "s3"));
        System.out.println("�ж�mySet���Ƿ���Ԫ��s1��"+jedis.sismember("mySet", "s1"));
        System.out.println("�ж�mySet���Ƿ���Ԫ��s5��"+jedis.sismember("mySet", "s5"));
        //����Set֮���Ԫ���ƶ�
        System.out.println(jedis.sadd("mySet1", "s1","s2","s4","s3","s0","s8","s7","s5"));
        System.out.println(jedis.sadd("mySet2", "s1","s2","s4","s3","s0","s8"));
        System.out.println("��mySet1�е�Ԫ��e1ɾ��������mySet3�У�"+jedis.smove("mySet1", "mySet3", "s1"));
        System.out.println("��mySet1�е�Ԫ��e2ɾ��������mySet3�У�"+jedis.smove("mySet1", "mySet3", "s2"));
        System.out.println("mySet1�е�����Ԫ�أ�"+jedis.smembers("mySet1"));
        System.out.println("mySet3�е�����Ԫ�أ�"+jedis.smembers("mySet3"));
	}
	@Test
	public void test6() {
		Jedis jedis = getJedis();
		jedis.flushDB();
        Map<String,String> map = new HashMap<String,String>();
        map.put("k1","v1");
        map.put("k2","v2");
        map.put("k3","v3");
        map.put("k4","v4");
        //����һ��ɢ�У�����������ֵ������
        jedis.hmset("hash",map);
        //����һ����ֵ��
        jedis.hset("hash", "k5", "v5");
        //����Map<String,String>
        System.out.println("��ȡhash�����м�ֵ�ԣ�"+jedis.hgetAll("hash"));
        //����Set<String> ������Java��Map��keySet����
        System.out.println("��ȡhash�����м�Ϊ��"+jedis.hkeys("hash"));
        //����List<String> ������Java��Map��values����
        System.out.println("��ȡhash������ֵ��"+jedis.hvals("hash"));
        //Map����������������������֮���ֵ
        System.out.println("��k6�����ֵ����һ�����������k6�����������k6��"+jedis.hincrBy("hash", "k6", 6));
        System.out.println("��ȡhash�����м�ֵ�ԣ�"+jedis.hgetAll("hash"));
        System.out.println("��k6�����ֵ����һ�����������key6�����������key6��"+jedis.hincrBy("hash", "k6", 3));
        System.out.println("��ȡhash�����м�ֵ�ԣ�"+jedis.hgetAll("hash"));
        System.out.println("ɾ��һ�����߶����ֵ�ԣ�"+jedis.hdel("hash", "k2"));
        System.out.println("��ȡhash�����м�ֵ�ԣ�"+jedis.hgetAll("hash"));
        System.out.println("��ȡhash�м�ֵ�Եĸ�����"+jedis.hlen("hash"));
        //�ж�ɢ���Ƿ����ĳ��key��boolean
        System.out.println("�ж�hash���Ƿ����k2��"+jedis.hexists("hash","k2"));
        System.out.println("�ж�hash���Ƿ����k3��"+jedis.hexists("hash","k3"));
        //��ȡɢ��ĳ��key��ֵ���������򷵻�null
        System.out.println("��ȡhash�е�ֵ��"+jedis.hmget("hash","k3"));
        //��ȡɢ�ж��key��ֵ���������򷵻�null
        System.out.println("��ȡhash�е�ֵ��"+jedis.hmget("hash","k3","k7"));
	}
	@Test
	public void test7() {
		Jedis jedis = getJedis();
		jedis.flushDB();
        Map<String,Double> map = new HashMap<>();
        map.put("k3",4.0);
        map.put("k4",5.0);
        //ΪSortedSet���һ������Ϊ3��Ԫ��,������ӵ�Ԫ�ظ���
        System.out.println(jedis.zadd("mySorted", 3,"k1"));
        //������set��Ӷ��Ԫ���Լ���Ӧ�ķ���,������ӵ�Ԫ�ظ���
        System.out.println(jedis.zadd("mySorted",map));
        //��ȡ����Ԫ��
        System.out.println("��ȡmySorted�е�Ԫ�أ�\r\n  "+jedis.zrange("mySorted", 0, -1));
        //��ȡ����Ԫ���Լ�Ԫ�ض�Ӧ�ķ���
        System.out.println("��ȡmySorted�е�Ԫ���Լ�������\r\n  "+jedis.zrangeWithScores("mySorted", 0, -1));
        System.out.println("��ȡmySorted�е�����Ԫ�أ�\r\n  "+jedis.zrangeByScore("mySorted", 0,100));
        //��ȡָ��������Χ��Ԫ��
        System.out.println("��ȡmySorted�е�Ԫ�أ�\r\n  "+jedis.zrangeByScoreWithScores("mySorted", 0,100));
        System.out.println("��ȡmySorted��k2�ķ�ֵ��"+jedis.zscore("mySorted", "k2"));
        System.out.println("��ȡmySorted��k2��������"+jedis.zrank("mySorted", "k2"));
        //ɾ���ɹ�����1��ʧ�ܷ���0
        System.out.println("��mySorted�е�Ԫ��k3ɾ����"+jedis.zrem("mySorted", "k3"));
        System.out.println("��ȡmySorted�е�Ԫ�أ�\r\n  "+jedis.zrange("mySorted", 0, -1));
        System.out.println("��ȡmySorted��Ԫ�صĸ�����"+jedis.zcard("mySorted"));
        System.out.println("mySorted�з�ֵ��1-4֮���Ԫ�صĸ�����"+jedis.zcount("mySorted", 1, 4));
        //��ֵ��������
        System.out.println("key2�ķ�ֵ����5��"+jedis.zincrby("mySorted", 5, "k2"));
        System.out.println("key3�ķ�ֵ����4��"+jedis.zincrby("mySorted", 4, "k3"));
        System.out.println("mySorted�е�����Ԫ�أ�\r\n  "+jedis.zrange("mySorted", 0, -1));
	}
	@Test
	public void test8() throws InterruptedException {
		Jedis jedis = getJedis();
        jedis.flushDB();
        System.out.println("�ж�ĳ�����Ƿ���ڣ�"+jedis.exists("name"));
        System.out.println("������ֵ�ԣ�"+jedis.set("name", "zhangsan"));
        //�жϼ��Ƿ����
        System.out.println(jedis.exists("name"));
        System.out.println("������ֵ�ԣ�"+jedis.set("pwd", "pwd"));
        System.out.println("��ȡ���е�key��");
        Set<String> keys = jedis.keys("*");
        //ѭ��������е�key
        keys.forEach((key) -> System.out.println( "         " + key));
        System.out.println("ɾ����pwd:"+jedis.del("pwd"));
        System.out.println("�жϼ�pwd�Ƿ���ڣ�"+jedis.exists("pwd"));
        //���ù���ʱ�䣬��λΪ�룬����1��ʾ���óɹ�
        System.out.println("���ü�name�Ĺ���ʱ��Ϊ5s:"+(jedis.expire("name", 5)==1?"�ɹ�":"ʧ��"));
        TimeUnit.SECONDS.sleep(2);
        //����ʣ��ʱ�䣬��λ��
        System.out.println("name��ʣ������ʱ�䣺"+jedis.ttl("name"));
        //��������ʱ��
        System.out.println("����name�Ĺ���ʱ�䣺"+jedis.persist("name"));
        //��2.8�����ϰ汾������-1��ʾû�����ù���ʱ�䣬����-2��ʾ��������
        //��û�����ù���ʱ����߼������ڶ�����-1
        System.out.println("name��ʣ������ʱ�䣺"+jedis.ttl("name"));
        System.out.println("name���洢��ֵ�����ͣ�"+jedis.type("name"));
	}
	@Test
	public void test12() {
		Jedis jedis = getJedis();
		jedis.del("sorted");
		jedis.del("myList");
	}
}
