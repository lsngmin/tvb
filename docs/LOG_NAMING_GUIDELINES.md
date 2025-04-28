# ✨ LogAction / LogStatus Naming Convention

## 🔍 Purpose

- 로그 작성 시 **Action** (비즈니스 작업)과 **Status** (처리 상태)를 일관성 있게 작성하기 위해 정리한 가이드입니다.
- 일관성 있는 로그 형식을 확립하고, 로그 분석 및 모니터링 시스템 연동을 용이하게 합니다.

---

## 🖊️ LogAction Naming Rule

### 정의
> 로그에 기록될 **주요 비즈니스 행위**(Action)를 명확하게 표현합니다.

### 작성 규칙
1. **비즈니스 단위**를 기준으로 명명합니다.
    - 예: `UserRegistration`, `UserAuthentication`, `ProfileUpdate`

2. **동사는 제거**하고, **명사형**으로 작성합니다.
    - `RegisterUser` ❌  → `UserRegistration` ✅

3. **PascalCase** (단어마다 첫 글자 대문자) 스타일을 사용합니다.
    - `userregistration` ❌ → `UserRegistration` ✅

4. 같은 행위에 대해서는 **프로젝트 전체에서 동일한 Action 이름**을 사용합니다.

5. 특수한 경우에는 **주석 또는 문서에 명확히 설명**을 추가합니다.

---

## 🖊️ LogStatus Naming Rule

### 정의
> 로그에 기록될 **처리 상태(Status)**를 명확하고 단순하게 표현합니다.

### 작성 규칙
1. **단일 단어 또는 PascalCase 형식**으로 작성합니다.
    - 예: `SUCCESS`, `FAILURE`, `PENDING`

2. **성공/실패/진행 상태**를 명확하게 구분합니다.
    - 성공: `SUCCESS`, `COMPLETED`
    - 실패: `FAILURE`, `VALIDATION_FAILED`

3. **진행 중인 상태**도 명확히 표현할 수 있습니다.
    - 예: `IN_PROGRESS`, `REQUEST_RECEIVED`, `TIMEOUT`

4. **긴 문장 사용은 금지**하고, 항상 간결한 단어로 작성합니다.

5. 상태 이름은 **누가 봐도 의미가 명확해야** 합니다.

---

## 🔖 Naming Example

| 구분 | 예제 | 설명 |
|:---|:---|:---|
| Action | `UserRegistration` | 회원가입 비즈니스 로직 실행 |
| Action | `UserAuthentication` | 사용자 로그인 프로세스 |
| Action | `ProfileUpdate` | 회원정보 수정 요청 |
| Status | `SUCCESS` | 정상적으로 처리 완료 |
| Status | `FAILURE` | 처리 실패 발생 |
| Status | `REQUEST_RECEIVED` | 요청 수신 완료 |
| Status | `REQUEST_COMPLETED` | 요청 정상 완료 |
| Status | `VALIDATION_FAILED` | 입력값 검증 실패 |

---

## 🔍 Additional Notes

- **ErrorCode**가 존재하는 경우, `Status` 대신 **ErrorCode**를 로그에 기록할 수 있습니다.
- 신규 비즈니스 로직 추가 시에는 팀원과 협의하여 Action/Status를 업데이트하고 문서화합니다.
- 작성된 Action/Status는 서비스별, 도메인별로 분리하여 관리할 수 있습니다.

---

✨ **이 가이드는 운영 모니터링과 장애 대응, 로그 분석의 일관성과 효율성을 높이기 위해 작성되었습니다.**

