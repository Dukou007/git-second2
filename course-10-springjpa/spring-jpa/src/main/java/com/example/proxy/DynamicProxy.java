package com.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class DynamicProxy {
  static interface Subject{
    void sayHi();
    void sayHello();
  }
  
  static class SubjectImpl implements Subject{

    @Override
    public void sayHi() {
      System.out.println("hi");
    }

    @Override
    public void sayHello() {
      System.out.println("hello");
    }
  }
  
  static class ProxyInvocationHandler implements InvocationHandler{
    private Subject target;
    public ProxyInvocationHandler(Subject target) {
      this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      System.out.print("say:");
      return method.invoke(target, args);
    }
    
  }
  
  public static void main(String[] args) {
    Subject subject=new SubjectImpl();
    Subject subjectProxy=(Subject)Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), new ProxyInvocationHandler(subject));
    subjectProxy.sayHi();
    subjectProxy.sayHello();
    
  }
}