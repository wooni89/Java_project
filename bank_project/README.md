# 은행 고객관리 프로그램

## app-01

- 리터럴을 사용하여 데이터 출력

## app-02

- 변수를 사용하여 데이터 저장

## app-03

- 키보드로 값을 입력 받기

## app-04

- 배열, 반복문, 상수 활용

## app-05, app-06

- 조건문 활용하여 흐름제어
  - if ~ else
  - for 문활용
  - switch
  - while

## app-07

- static 메서드를 정의하고 호출

## app-08

- 정의된 static 변수를 사용하여 메소드간에 변수 공유
- 리팩토링
  - 사용자에게서 입력받은 기능을 메서드로 분리하여 정의


## app-09

- 클래스를 사용하여 리팩토링

## app-10

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

## app-11

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
  -  인스턴드 필드 생성자 추가
  -  getter / setter 추가
  -  스태틱 필드 및 생성자 활용
  - 입출금을 위한 스태틱 필드 추가