## Chapter 2.9 try-finally 보다는 try-with-resources를 사용하라



자바 라이브러리에는 close 메서드를 호출해서 달아줘야하는 자원이 많다. InputStream, OutputStream, java.sql.Connection 등이 좋은 예다. 자원 닫기는 클라이언트가 놓치기 쉬워서 성능상 문제가 발생할 수 있다.



```java
// 코드 9-1 try-finally - 더 이상 자원을 회수하는 최선의 방책이 아니다! (47쪽)
    static String firstLineOfFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path)); // 여기엇 예외가 나면
        // 아래의 close도 예외가 발생할 것이다.
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }
```



```java
    // 코드 9-2 자원이 둘 이상이면 try-finally 방식은 너무 지저분하다! (47쪽)
    static void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                int n;
                while ((n = in.read(buf)) >= 0)
                    out.write(buf, 0, n);
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

// try에서 사용하는 자원이 많아질수록 코드가 더러워 진다.
```



위의 두 코드에서 미묘한 결점이 존재하는데, 이러한 결점은 try나 finally 모두 예외가 발생할 수 있다는 점이다. 이런 모든 예외를 위처럼 처리하게 된다면 더러운 코드가 발생하게 될 것이다. 이런 것은 try-with-resource가 나온 후로는 많이 개선이 되었다. 이 구조를 사용하려면 AutoCloseable 인터페이스를 구현해야 한다.수 많은 라이브러리가 AutoCloseable을 구현해 있다.



아래는 try-with-resource로 재구현한 것이다.

```java
    static String firstLineOfFile(String path) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new FileReader(path))) {
            return br.readLine();
        }
    }
```

```java
    // 코드 9-4 복수의 자원을 처리하는 try-with-resources - 짧고 매혹적이다! (49쪽)
    static void copy(String src, String dst) throws IOException {
        try (InputStream   in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[BUFFER_SIZE];
            int n;
            while ((n = in.read(buf)) >= 0)
                out.write(buf, 0, n);
        }
    }
```



##### 상당히 코드도 깔끔해졌고 보기도 좋아졌다. 더군다나 읽기도 수월해졌고ㅎ 이렇게 코드를 작성하면 예외가 씹히지 않고 숨겨져 있는 예외도 모두 표시(숨김으로)가 된다.



try-with-resources 문에서도 try-finally와 같이 catch 절을 사용할 수 있다.

```java
// 코드 9-5 try-with-resources를 catch 절과 함께 쓰는 모습 (49쪽)
    static String firstLineOfFile(String path, String defaultVal) {
        try (BufferedReader br = new BufferedReader(
                new FileReader(path))) {
            return br.readLine();
        } catch (IOException e) {
            return defaultVal; // try-with-resouces도 catch절 사용가능 !
        }
    }
```



```text
핵심정리

꼭 회수해야하는 자원이 있을 때는 try-with-resouces 문을 사용하기를 권장한다. 예외는 없다. 더 짧은 코드, 가독성 좋은 코드, 예외 정보도 더 풍부하다.
```

