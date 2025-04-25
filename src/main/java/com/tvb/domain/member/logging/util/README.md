## ✨ Log Level Usage Guide (for Production-Grade Applications)   
####  Last Modified 25.04.25


### ⚠️ Log Level Policy

```text
FATAL - 사용하지 않습니다. FATAL은 개발 자체가 잘못된 경우를 의미합니다.
ERROR - 처리 가능한 예외 포함, 미처 처리하지 못한 예외를 출력합니다.
WARN  - 사용자의 잘못으로 인해 발생한 예외 상황입니다.
INFO  - 정상적인 흐름 중 중요한 비즈니스 이벤트를 출력합니다.
        비즈니스 메서드당 2~3개 이하로 제한합니다.
DEBUG - 프로덕션 환경에서는 출력하지 않습니다. 개발자가 흐름을 이해하기 위한 레벨입니다.
TRACE - 모든 내부 흐름을 추적하는 가장 상세한 로깅 레벨입니다.
```

---

### 🔢 Log Format Rule

> **비즈니스 로직 중심의 로그 추적**, **흐름 단위 상태 구분**, **핵심 정보 노출**, **일관된 분석**을 위한 로그 포맷입니다.

#### ▶️ Parameters

| 파라미터 | 설명                                                                                                                                                                         |
|----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `a` (Action)  | 로그의 주된 행동을 나타냅니다. 하나의 business-layer 액션 단위를 의미합니다. <br> 예: 회원가입 메서드 호출 시 `"UserRegistration"`                                                                              |
| `s` (Status)  | 로그 상태를 의미하며, business-layer 또는 presentation-layer 의 프로세스 시작/종료/예외 상태를 나타냅니다. <br> 예: `"RequestReceived"`, `"RequestCompleted"`, 또는 예외 클래스 이름                               |
| `d` (Details) | 로그 상세 내용입니다. 핵심 정보 (ex. Email, ID 등) 표시. Password 또는 Token 등 민감한 정보는 제외합니다.<br/>에러 발생 시 에러 메세지를 출력합니다. (예: `Please enter a valid email address`) <br> 예: `Email`,`Profile` |
| `args`        | 로그 출력용 메타 정보. 순서 중요:                                                                                                                                                       
- `args[0]`: HTTP 메서드 (예: POST, GET)
- `args[1]`: URI 경로 (예: /api/v1/register)
- `args[2]`: HTTP 상태 코드 (예: 200, 400)
- `args[3]`: 로그에 포함할 주요 값 (예: "ia****@gmail.com") | 

#### ▶️ Return

- 지정된 형식으로 포맷된 로그 메시지를 반환합니다.

```java
String f = String.format(
    "\"%-4s %s\" %3s - %s | %-16s | %s(%s)",
    args[0], args[1], args[2], a, s, d, args[3]
);
return f;
```

---

### 🔍 Example

```java
log.info(formatMessage(
  "UserRegistration",
  "RequestCompleted",
  "ia****@gmail.com",
  "POST", "/api/v1/register", "200", "ia****@gmail.com"
));
```

Output:
```
"POST /api/v1/register" 200 - UserRegistration | RequestCompleted | Email(ia****@gmail.com)
```

---

### 🏆 운영 레벨 권장 설정

- **개발 환경**: TRACE, DEBUG, INFO, WARN, ERROR
- **운영 환경**: INFO, WARN, ERROR (DEBUG 이하는 비활성화)

> 슬랙 알림, APM 연동은 WARN/ERROR 수준으로 필터링

---

### 🚀 Tips

- 로그 남발은 분석을 어렵게 만듭니다. 목적을 갖고 작성합니다.
- 로그 메시지는 사람이 읽고 이해할 수 있게 작성합니다.
- 개인정보 포함 시 마스킹 필수!
- 비즈니스 단위별 ACTION 명세 문서화 추천!

