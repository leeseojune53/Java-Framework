# Java-Framework
Toy Framework

---
V1(CGLIB AOP)
![java-framework-aop-1](https://github.com/leeseojune53/Java-Framework/assets/61784568/c33838a2-a5dd-4802-aa2e-84f098c0d756)

1. RootApplication에서 정의한 annotationCallBackMap을 이용해서 AnnotationAOPProcessor을 생성한다. 형식은 아래와 같다.
   ```java
   Map.of(Component.class,
                (obj, method, args, proxy, chain) -> {
                    System.out.println("Class: " + obj.getClass().getName() + "  Method : " + method.getName()
                            + " Component Annotated Method");
                    chain.next(obj, method, args, proxy);
                    return null;
                })
   ```
2. AnnotationAOPProcessor의 getMethodAopFunction()을 이용해서 Map에 들어가있는 Annotation을 사용한 클래스 / 메소드 정보를 받는다.
   이를 통해 ProxyPart를 생성한다.
3. 생성한 ProxyPartList를 ClassMetadata의 getProxy에 넘겨준다.
4. getProxy에서 ProxyPart에 있는 List<MultiCallback>으로 각 메소드별 MethodChain을 생성하고, 이를 CGLIB Enhancer의 Callback에 넣는다.
5. 위에서 설정한 정보로 Proxy객체를 생성한다, 생성할 때 생성자에서 받는 Object가 있으면, 생성된 Service(Spring에서는 Application Context / IoC Container의 역할, 이하 Application Context)를 가지고 있는 Map<Class, Object>에 해당하는 ClassType이 있으면 주입해주고, 없으면 retryList에 add하고 넘어간다.
6. ProxyPart List의 size와 Application Context에 있는 Object의 개수가 같지 않으면 5번 과정을 반복한다. 이는 DI를 받을 때 아직 Argument로 받을 ProxyObject가 생성되지 않았을 때를 위함이다.
