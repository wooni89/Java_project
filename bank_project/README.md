# 은행 고객관리 프로그램

## 1. 리터럴을 사용하여 데이터 출력하기

- 리터럴을 사용하는 방법

## 2. 변수를 사용하여 데이터를 저장하기

- 변수를 사용하는 방법

## 3. 키보드로 값을 입력 받기

- 키보드로 값을 입력 받는 방법

## 4. 배열과 반복문을 이용하여 여러 개의 데이터를 입력 받기

- 배열 활용
- 반복문 활용
- 상수 활용

## 5-6. 조건문을 활용하여 실행 흐름을 제어하기

- if ~ else ~ 조건문 활용
- switch 활용
- break 활용
- while 활용

## 7. 기능 단위로 명령문 묶기 : 메서드 사용법

- static 메서드를 정의하고 호출하는 방법 

## 8. 메서드 간에 변수 공유하기 : 스태틱 변수 사용법

- static 변수를 정의하고 사용하는 방법
- 리팩토링
  - 사용자에게서 입력 받는 기능을 메서드로 분리하여 정의(메서드 문법 활용)


## 9. 메서드를 역할에 따라 분류하기

- 클래스를 사용하여 리팩토링

## 10. 메뉴 구성 및 CRUD 구현

- App.java
  - printMenu 구성추가
  - promptContinue 삭제
    - MemberHandler.inputAccounts 메소드 추가로 인한 삭제
- MemberHandler
  - inputAccounts(계좌개설) 메소드 추가
  - viewAccount(잔고확인) 메소드 추가
  - updateAccount(계좌정보변경) 메소드 추가
  - deleteAccount(계좌삭제) 메소드 추가
    - 기존데이터에서 삭제된 데이터 공간에 빈 메모리에 할당하여 저장 구성할것!
- Prompt
  - inputInt 메소드 추가 (int형 입력)

## 11.생성자, 셋터, 겟터 도입

- 휴먼에러 오입력방지를 위한 선택항목 모두 반복문 적용

- App.java
  - 입금 출금 기능 활성화
  - 메뉴 UI 변경
- MemberHandler
  - Member.java 추가하여 레퍼런스배열화
  - inputAccount(계좌개설)
    - 은행선택구분 switch문 에서 if문으로 변경
    - 입출금시 계좌번호 및 비밀번호 입력 추가
- Member 클래스 추가
  - 인스턴드 필드 생성자 추가
  - getter / setter 추가
  - 스태틱 필드 및 생성자 활용  
  - 입출금을 위한 스태틱 필드 추가

## 12. 인스턴스 필드와 인스턴스 메서드, 생성자와 의존 객체 주입

- BoardHandler 클래스에 인스턴스 필드 및 메서드 적용
- 향후 확장성을 고려하여 MemberHandler 크래스에도 인스턴스 필드와 인스턴스 메서드를 적용
  - 그래서 실무에서는 대부분의 클래스가 인스턴스 필드와 인스턴스 메서드로 구성된다.
- 향후 확장성을 고려하여 Prompt 크래스에도 인스턴스 필드와 인스턴스 메서드를 적용
  - 생성자 도입: Scanner 사용할 입력 도구를 지정할 수 있게 한다.
- 의존 객체 주입의 개념과 구현
  - 생성자를 통해 Prompt 객체를 Handler에 주입

### 복사/붙여넣기를 이용한 CRUD 구현

- 게시글 CRUD 기능 추가
- Value Object, Handler 클래스 추가
- Prompt 클래스 리팩토링

## 13. 다형성을 이용하여 범용으로 사용할 수 있는 목록 클래스 만들기

- 목록 관리 범용 클래스 ArrayList 정의
  - 다형성의 polymorphic variable 문법 활용
- equals() 메서드와 오버라이딩 활용
  - Object 클래스와 상속
  - Member와 Board 클래스에 적용
- 오버로딩을 활용하여 생성자를 추가
  - Member와 Board 클래스 적용
- MemberHandler와 BoardHandler에 적용

- 핸들러에서 인스턴스 목록을 다루는 기능을 별도의 클래스로 분리
  - UI가 CLI에서 윈도우 또는 웹으로 바뀌더라도 인스턴스 목록 다루는 기능은 재사용 가능
- 배열 크기 자동 증가 기능 추가

### GRASP 패턴: Information Expert 적용

- 메뉴 기능을 각 핸들러에게 위임
  - 기능을 수행하는데 필요한 정보를 가지고 있는 객체에 역할 부여
  - CRUD 메뉴 기능은 핸들러로 이전
- App 클래스는 메인 메뉴 제공

### 인터페이스를 이용한 객체 사용 규칙 정의

- 인터페이스 문법으로 핸들러의 실행 규칙 정의
- 인터페이스에 정의한 대로 핸들러 구현
- 인터페이스에 정의한 대로 핸들러 실행

### 인스턴스 목록 제어 기능을 별도의 클래스로 캡슐화: 재사용성 높임

- 핸들러에서 인스턴스 목록을 다루는 기능을 별도의 클래스로 분리
  - UI가 CLI에서 윈도우 또는 웹으로 바뀌더라도 인스턴스 목록 다루는 기능은 재사용 가능
- 배열 크기 자동 증가 기능 추가

## 14. LinkedList 자료구조 구현하기

- 목록 관리 범용 클래스 LinkedList 정의
  - LinkedList 구동원리 이해 및 구현
  - 중첩 클래스 활용
- MemberHandler와 BoardHandler에 적용

## 15. 인터페이스를 이용하여 List 사용 규칙 정의하기

- 목록 관리 객체의 사용 규칙을 인터페이스 정의
  - List 인터페이스 정의
  - ArrayList, LinkedList에 List 인터페이스 적용
- MemberHandler와 BoardHandler에 적용
  - List 구현체를 생성자를 통해 주입: DI(Dependency Injection) 적용

## 16. Stack, Queue 자료구조 구현하기

- Stack과 Queue의 구동원리 이해 및 구현
- Stack 적용
  - Prompt 클래스의 서브 클래스 MenuPrompt 정의
  - MenuPrompt에서 Stack을 이용하여 프롬프트 제목에 breadcrumb 기능을 적용
- Queue 적용
  - MenuPrompt 클래스에 메뉴 출력 기능을 추가
    - App, BoardHandler, MemberHandler 변경
  - MenuPrompt 클래스에 입력한 명령어의 history 기능을 추가

## 17. Composite, Command, Observer 디자인 패턴, 추상 클래스/메서드 활용하기

- Composite 패턴을 활용하여 메뉴 구현하기
  - BreadcrumbPrompt에 적용
  - Menu, MenuGroup 클래스 정의
- Observer 패턴을 활용하여 메뉴 명령 처리하기
  - ActionListener 인터페이스 정의
  - Menu와 리스너 객체 연결
- Command 패턴을 활용하여 메뉴 기능 구현하기
  - BoardHandler, MemberHandler에 적용
  - ActionListener 인터페이스 활용
  - BoardXxxListener, MemberXxxListener 클래스로 분해
- Generalization(상속) 수행 
  - AbstractBoardListener 추상 클래스 정의
    - 추상 메서드 도입

## 18. 제네릭을 사용하여 타입을 파라미터로 다루기

- ArrayList, LinkedList, Stack, Queue에 제네릭 적용하기
- T[] toArray(T[]) 메서드 추가하기

- Iterator 디자인 패턴을 활용하여 목록 조회 기능을 캡슐화하기

- GoF의 디자인 패턴 중 Iterator 패턴의 동작원리 이해 및 구현
- ArrayList, LinkedList, Stack, Queue에 적용
- 중접 클래스 문법을 이용하여 Iterator 구현하기
  - static/non-static nested 클래스 문법을 활용하는 방법
  - local/anonymous 클래스 문법을 활용하는 방법

## 19.자바 Collection API 사용하기

- 목록을 다루는 기존 클래스를 자바 컬렉션 API 로 교체

## 20.File I/O API를 이용하여 데이터를 바이너리 형식으로 입출력하기

- FileInputStream/FileOutputStream 사용법
- 바이너리 형식으로 데이터를 입출력하는 방법

## 21. 상속을 이용하여 primitive type과 String 출력 기능을 추가하기

- 상속을 이용하여 바이트 입출력 기능을 확장하기
  - DataInputStream = FileInputStream 클래스 + primitive type/String 값 읽기
  - DataOutputStream = FileOutputStream 클래스 + primitive type/String 값 쓰기

## 22.입출력 성능을 높이기 위해 버퍼 기능 추가하기

- 기존의 클래스에 버퍼 기능을 추가한다.
  - BufferedDataInputStream = DataInputStream + 버퍼 기능
  - BufferedDataOutputStream = DataOutputStream + 버퍼 기능

## 23. 입출력 기능 확장에 상속 대신 Decorator 패턴을 적용하기

- 상속 vs Decorator 패턴(GoF)
  - 기존 코드를 손대지 않고 기능 확장하는 방법
  - 상속: 기능 확장 용이
  - Decorator: 기능 확장 및 기능 제거 용이
- BufferedDataInputStream 분해
  - BufferedInputStream, DataInputStream, FileInputStream
- BufferedDataOutputStream 분해
  - BufferedOutputStream, DataOutputStream, FileOutputStream

## 24. Java Stream API 로 교체하기

- 입출력 관련 클래스를 자바 스트림 클래스로 교체
  - java.io.* 패키지의 클래스 **사용**

## 25. 인스턴스를 통째로 입출력하기(객체 직렬화)

- ObjectInputStream/ObjectOutputStream 사용법
  - java.io.Serializable 인터페이스 사용법
  - transient modifier 사용법

## 26. character stream API를 사용하여 CSV 텍스트 형식으로 입출력하기

- CSV 형식으로 데이터를 읽고 쓰는 법
- FileReader/FileWriter 사용법

### 리팩토링: Factory Method 패턴(GoF), Information Expert 패턴(GRASP)

- CSV 데이터 생성을 Board 클래스에 맡기기
  - Information Expert 패턴 적용
- CSV 데이터를 가지로 Board 클래스 생성하기
  - Factory Method 패턴 적용
  - Reflection API 사용법
    - Class, Method 사용법

## 27. 데이터의 등록, 조회, 수정, 삭제 기능을 캡슐화하기 : DAO 객체 도입

- XxxListener에서 데이터를 조작하는 코드를 캡슐화하여 별도의 클래스로 분리
- 인터페이스로 DAO 객체 사용법을 정의

### JSON 형식으로 입출력하기

- JSON 형식으로 데이터를 읽고 쓰는 법
- Gson 라이브러리 사용법

## 28. 네트워킹을 이용하여 데이터 공유하기 : Client/Server 아키텍처로 전환

- 네트워크 프로그래밍
  - Client와 Server
  - 프로토콜에 따라 애플리케이션 간에 데이터를 주고 받기

## 29. Reflection API를 활용하여 DAO 메서드 호출을 자동화하기

- DAO 프록시 객체를 자동 생성
- java.lang.reflect.Proxy 클래스 사용
- 서버의 DAO 메서드 호출을 자동화 

## 30. 여러 클라이언트의 요청을 순차적으로 처리하기: Stateful 방식

- 서버앱 예외처리 적용
- 클라이언트 요청을 순차적으로 처리하는 방법 적용

## 31. 여러 클라이언트의 요청을 순차적으로 처리하기: Stateless 방식

- Stateless 방식으로 통신하는 방법적용

## 32. 여러 클라이언트 요청을 동시에 처리하기: Thread 적용

- ServerApp에 Thread적용
- 스레드 재사용하기 : 스레드풀(thread pool) 구현
  - Pooling 기법을 활용하여 스레드를 재사용하는 방법
  - GoF의 FlyWeight 디자인 패턴(풀링 기법)을 적용하여 스레드풀을 구현하는 방법

## 33. 스레드 재사용하기 : 자바에서 제공하는 스레드풀(thread pool) 사용

- Excutors/ExcutorService 적용

## 34. DBMS 도입

- DBMS와 연동하여 작업하는 DAO 구현

## 35. SQL 삽입공격 차단

- PreparedStatement 적용

## 36. 로그인 적용

- 로그인기능 구현

## 37. Application Server 아키텍터로 전환

- 애플리케이션 서버 아키텍처의 특징과 구현
- Executor를 이용하여 스레드를 풀링

## 38.Mybatis SQL-mapper 프레임워크 사용

- mybatis 프레임워크의 구동 원리 및 사용법
- GoF의 Proxy 패턴을 이용하여 SqlSessionFactory 객체의 기능을 변경

## 39. 리스너 실행에 Facade 패턴 적용

- GoF의 Facade 패턴의 동작원리 이해와 적용
- ActionListener 실행에 Facade 객체를 사용

## 40. IoC 컨테이너 적용

- IoC 컨테이너의 구동 원리와 구현
- 리스너 객체를 IoC 컨테이너로 관리

## 41. 웹 애플리케이션 서버 구조로 전환하기 - 웹 기술 도입

- Netty, Reactor 라이브러리를 사용하여 웹서버를 구축
- 웹브라우저를 이용하여 클라이언트를 구축

## 42. 파일 업로드 다루기 - multipart/form-data POST 요청 파라미터 인코딩

- 네이버클라우드 mysql DBMS 사용
- apache.org의 라이브러리를 이용하여 multipart/form-data 파라미터 사용
- Servlet API 사용

## 43. 서블릿 컨테이너 삽입하기

- 웹 애플리케이션에 서블릿 컨테이너를 포함

## 44. 첨부파일을 네이버 클라우드의 스토리지 서비스에 저장하기

- 네이버 스토리지 서비스 사용
- 첨부파일을 스토리지 서비스에 업로드
- 네이버 클라우드의 Image Optimizer를 이용하여 썸네일 이미지 다루기
- 네이버 클라우드의 Image Optimizer 사용
- 고객정보 상세보기, 목록에 사진 추가

## 45. forward/include/refresh/redirect 적용

- 오류 메시지 출력에 forward 및 refresh 사용
- 상단 메뉴 및 하단 정보 출력에 include 사용 (header, footer 사용)
- 등록 완료 후 목록 페이지로 이동할 때 redirect 사용

## 46. 쿠키와 ServletContext 보관소 활용

- 쿠키를 이용하여 로그인 할 때 입력한 이메일을 보관
- 서블릿들이 공통으로 사용하는 객체를 ServletContext 보관소에 보관

## 47. JSP를 이용하여 MVC 모델1 구조로 변경

## 48. 서블릿을 결합하여 MVC 모델2 구조로 변경

## 49. Front Controller 디자인 패턴 도입

- front 컨트롤러와 페이지 컨트롤러 분리

## 50. 페이지 컨트롤러를 POJO로 전환