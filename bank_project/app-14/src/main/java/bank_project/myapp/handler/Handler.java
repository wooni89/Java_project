package bank_project.myapp.handler;

// 핸들러 사용규칙
// - 메서드 호출 규칙을 정의
// 메서드 시그니처와 리턴 타입을 정의
// 메서드 몸체는 작성하지 않는다.
// 왜? 호출 규칙만 정의하는것
public interface Handler {
  void execute();
}
