Line LIN 에서는 메인 스토리지를 Redis에서 MonggoDB로 전환

### 왜 전환하는가?

레디스를 메인 스토리지로 사용하면서 알림 관련 주요 데이터를 레디스에 저장해 서빙하고 있다. 하지만 늘어가는 알림 종류와 스펙, 새 계정 유 등의 영향으로 레디스 사용량이 증가함, 80% 정도까지 사용되었고, 스왑인/아웃이 발생하면서 레디스 fail over 또한 늘었다.

### 급한 처리

- 레디스 스케일 아웃
- 재시작을 통한 메모리 파편화 감소 (사용가능한 메모리가 충분히 존재하지만 할당이 불가능한 상태, 메모리 단편화가 발생되었다고 함)
- 데이터 압축? 
- sortedset 데이터 구조 변경
- 만료된 데이터 주기적 삭제 처리

### 왜 MongoDB??

- 문서 데이터베이스
  - 알림의 데이터 형태가 문서 데이터베이스에 적합했기 때문이다. 

```json
{
  "owner": "-",
  "notificationId": "-",
  "serviceType": "-",
  "categoryType": "-",
  "notificationType": "-",
  "templateType": "-",
  "itemKey": "-",
  "originsCount": 1,
  "origins": [
    {
      "createdTimeMsec": -,
      "createdDate": -,
      "sender": {-},
      "landing": "-",
      "component": {
        "profile": {-},
        "preview": {-},
        "buttons": {-},
        "icon": {-},
        "like": {-}
      },
      "searchKeys": {-},
      "messageArguments": {-}
    }
  ]
}
```

- 앱에서는 데이터 전체가 하나의 알림 객체 그 자체이다. 데이터의 부분적 사용 X
- 알림 객체는 상호 독립 > 정규화가 필요없음
- 알림 데이터 스펙은 기능이 추가되거나 변경되면서 바뀔 수 있다. > 스키마리스가 적합하다.
- 여러 개의 인덱스 구성이 가능하다 
- TTL 인덱스 지원 : 알림 데이터는 만료 기한이 있음. (LINE에선 1달로 처리함) 데이터 보관이 블필요하여 TTL 인덱스를 지정하여 만료 기한이 지난 데이터들은 삭제한다

> 빠른 접근이 필요한 데이터를 제외하고 MongoDB로 데이터 이관

### 문서가 커지며 oplog 보관기간 부족 현상

알림 병합이 이루어지며 1개의 게시글의 여러 명의 사람이 댓글을 작성한다면 각각의 사람에게서 알림이 오지 않고 여러 명으로 처리되어 1개의 알림으로 오도록 처리

레디스에서는 용량문제로 몽고로 전환하여 용량 문제는 해결되었지만 비대해진 문서 때문에 oplog 보관 기간 문제가 발생

```json
{
  "owner": "-",
  "notificationId": "-",
  "serviceType": "-",
  "categoryType": "-",
  "notificationType": "-",
  "templateType": "-",
  "itemKey": "-",
  "originsCount": n,
  "origins": [ // n명(최대 100명) 각 발신자의 알림 데이터 저장
    {
      "createdTimeMsec": -,
      "createdDate": -,
      "sender": {-},
      "landing": "-",
      "component": {
        "profile": {-},
        "preview": {-},
        "buttons": {-},
        "icon": {-},
        "like": {-}
      },
      "searchKeys": {-},
      "messageArguments": {-}
    },
    ... // n개의 데이터가 있다 
  ]
}
```



### 몽고 DB 쓰기 배타적 락에 따른 읽기 지연

- 이슈발생 : 읽기 지연 시간이 불규칙적으로 증가
- 원인 파악 : 특정 사용자에게서 나타나는 것으로 확인 되었고, 특정 사용자들은 일반 사용자보다 알림이 많고 단 시간에 받는다는 특징이 있다

1. 읽는 문서들의 범위가 크면 클수록 락에 걸리는 작업이 많고 시간이 오래 걸릴 것이다. 리스트를 읽을 때 기존에 WA에서 처리해 왔던 알림이 만료됐는지 혹은 조건을 충족하는지 등의 쿼리를 구체화해서 몽고디비로 위임했음. > 이는 디비에서 읽어오는 범위를 축소하게 만들었다

2. 만료 or 최대 개수를 초과한 알림을 삭제하는 처리를 배치로 전환, 기존에 알림을 삭제하는 로직은 실시간을 처리하고 있었다. 이는 실시간으로 실행이 되다보니 쓰기 락이 걸리게 되어서 락에 걸리는 빈도수를 증가 시켰다. > 실시간이 필요한지 파악한 후에 트래픽이 적은 새벽 시간에 작동하는 배치로 개발해 락 발생 빈도수를 줄임

   > 고려해야할 점은 전세계 서비스라고 생각한다면 각각의 서비스들마다 시간 대가 다르기 때문에 시간대가 고려 되어야 할 것 같다

이는 완벽한 해결책은 아님. 

몽고디비 5.0부터는 lock-free read operations를 지원하여 스토리지 레벨에서 해결가능

reference : https://engineering.linecorp.com/ko/blog/LINE-integrated-notification-center-from-redis-to-mongodb?ref=codenary

















