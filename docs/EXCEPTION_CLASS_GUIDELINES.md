
# ✨ Exception Class 작성 규칙

## 🔍 Purpose

- 프로젝트 전체에서 **일관성 있는 예외 처리**를 위해 Exception 클래스의 구조와 규칙을 정리합니다.
- **ErrorCode**와 예외 메시지를 기반으로 **구체적이고 의미 있는 예외 처리**가 가능하도록 합니다.

---

## 🖋️ Exception 설계 구조

### 1. **GlobalException**
- 프로젝트 최상위 공통 예외 클래스입니다.
- `RuntimeException`을 상속하고, 모든 도메인 예외의 부모 클래스 역할을 합니다.
- `ErrorCode`와 `value(거절된 값 등)`을 함께 관리합니다.

#### 예시:

```java
public class GlobalException extends RuntimeException {
    private final String value;
    private final ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.value = null;
        this.errorCode = errorCode;
    }

    public GlobalException(ErrorCode errorCode, String value) {
        super(errorCode.getMessage());
        this.value = value;
        this.errorCode = errorCode;
    }
}
```

---

### 2. **도메인별 통합 Exception**
- **각 도메인별 예외**(`AuthException`, `RegisterException`)는 `GlobalException`을 상속받습니다.
- 예를 들어, `AuthException`은 인증 관련 예외들을 관리하고, `RegisterException`은 회원가입 관련 예외들을 처리합니다.

#### 예시:

```java
public class RegisterException extends GlobalException {
    public RegisterException(ErrorCode errorCode, String rejectedValue) {
        super(errorCode, rejectedValue);
    }
}

public class AuthException extends GlobalException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
...
```

---

### 3. **세부 Exception 클래스**
- 도메인별로 **세부 예외** 클래스를 작성하여 구체적인 예외를 관리합니다.
- 각 도메인별 예외는 통합된 예외 클래스(`RegisterException`, `AuthException`)를 상속합니다.
- `static factory method`를 사용하여 예외를 생성합니다.

#### 예시:

```java
public class InvalidFormatException extends RegisterException {
    public static InvalidFormatException forInvalidNickName(String n) {
        return new InvalidFormatException(INVALID_NICKNAME_ERROR, n);
    }
    public static InvalidFormatException forInvalidUserId(String n) {
        return new InvalidFormatException(INVALID_USERID_ERROR, n);
    }
    public static InvalidFormatException forInvalidPassword(String n) {
        return new InvalidFormatException(INVALID_PASSWORD_ERROR, n);
    }

    public InvalidFormatException(ErrorCode errorCode, String value) {
        super(errorCode, value);
    }
}
```

```java
public class DataIntegrityViolationException extends AuthException {
    public static DataIntegrityViolationException forDuplicateUserId() {
        return new DataIntegrityViolationException(DUPLICATE_USER_ID);
    }

    public DataIntegrityViolationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
```

---

## 🔖 작성 시 주의사항

- 예외를 처리할 때 반드시 **`ErrorCode`**를 함께 전달하여 클라이언트 응답과 로깅 시 활용할 수 있도록 합니다.
- **`value`** 필드를 사용하여 예외 발생 당시 입력된 주요 데이터를 함께 기록합니다. (단, 개인정보는 반드시 마스킹)
- 예외 클래스를 작성할 때, **`static factory method`** 패턴을 사용하여 예외 생성 가독성을 높입니다.
- 예외 메시지는 **`ErrorCode`**에 기반하여 자동으로 생성되며, 추가적인 유효성 검사와 관련된 상세 메시지는 **`value`**에 포함시킵니다.

---

### 📝 참고 사항

- 예외 클래스에서 **`ErrorCode`**가 반드시 포함되도록 하여, 후속 처리에서 상태 코드, 메시지 등을 쉽게 관리할 수 있도록 합니다.
- 예외 발생 시, **스택 트레이스**를 포함해 `Logger`로 에러를 기록할 때 **`ErrorCode`**와 **`value`**를 기록하여 디버깅 및 운영 모니터링에 유용하게 활용합니다.

---

✨ **이 가이드는 예외 처리 일관성을 높이고, 유지보수를 용이하게 하며, 클라이언트에게 더 정확한 오류 정보를 전달하기 위해 작성되었습니다.**
