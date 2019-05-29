### ӳ����  
Mybatis��������������ֽ��������   
https://www.2cto.com/database/201409/338155.html   
1��sql��ǩ����������id��ǩ��sql��䣨ȥ�ظ���laravelģ�����棩  
2����������  
assiciation һ��һ  
collection һ�Զ�  
discrimination ����������Ů�����Ŀʵ���಻ͬ��  
3����̬����ʽ  
JDK��̬����  
```java
// �ӿ�  
public interface HelloService{  
    void sayHello(String name);  
}  
// ʵ����  
public class HelloServiceImpl implements HelloService {  
    @Override  
    public void sayHello(String name) {
        System.out.println("hello"+name);
    }
}
// �����ࣨbind�󶨷�����invoke��������ͨ������������Ƚ������������
public class HelloServiceProxy implements InvocationHandler {
    private Object target;
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        System.out.println("���붯̬����");
        Object result = null;
        System.out.println("��̬����ǰ");
        method.invoke(target, args);
        System.out.println("��̬�����");
        return result;
    }
}
// ���ã����������࣬bind�󶨽ӿڲ�����ʵ���࣬���÷�����
    public static void main(String[] args) {
        HelloServiceProxy helloHandler = new HelloServiceProxy();
        HelloService proxy = (HelloService)helloHandler.bind(new HelloServiceImpl());
        //ͨ������������
        proxy.sayHello("xxx");
    }
```
CGLIB��̬����  
����ӿ�  
4�����ô洢���̡��αꡢ�ֱ���ҳ  
���ò��� mode = OUT/IN   