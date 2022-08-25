# Redis

- look aside cache (Lazy Loading)
    - 캐시 DB에서 데이터 존재 유무 확인
    - data 존재하면 cache data 가져옴
    - data 없으면 db data 가져옴
    - db data cache 저장
- wrtie back

Redis는 single thread이다.

> 따라서 원자성을 보장한다.
> 

Spring data redis로 spring과 redis 시스템을 연결할 수 있음

### **2가지 접근방식을 가지고 있다**

1. RedisTemplate 사용
2. Redis Repository 사용

레이지 로딩은 클라이언트에게서 데이터가 필요로 할 대 cache에 로딩하는 전략입니다.

redis는 여러 방식의 데이터 형식을 제공하지만 대부분은 <key, value> 형태로 사용된다.

### **캐시 정책 (from woowabros)**

### **데이터 갱신**

데이터 갱신은 주저장소 > n차 redis > n-1차 reids > 1차 reids

> 내가 생각하는 이루어져야하는 캐시 정책?
> 
1. cache 데이터 확인
2. cache 데이터 없으면 db에서 가져옴
3.