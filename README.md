# spring-boot-exit-problem

This small project shows a problem if

    System.exit(SpringApplication.exit(ctx, () -> 42));

is executed within a controller method.

### Steps to reproduce:

1. checkout this repo
2. build a jar file with `mvn clean package`
3. start the shell script `./startup.sh`

### Explanation

There is only one java class: `ExitApplication`.

In it's `@PostConstruct` method the URL http://localhost:8080 is called,
and in the corresponding `@GetMapping` a shutdown is initiated by executing `System.exit(SpringApplication.exit(ctx, () -> 42));` - which means, that the app is stopped as soon as it is started.

Unfortunately, the intended exit status 42 is not always returned, instead also 0 is returned sometimes.

Since it can take some time until a different exit status appears, the shell script `startup.sh` starts the Spring Boot app over and over again.

The console output looks as follows:

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.6)

2024-06-14T13:51:51.327+02:00  INFO 56858 --- [exit] [           main] de.idon.exit.ExitApplication             : Starting ExitApplication v0.0.1-SNAPSHOT using Java 17.0.11 with PID 56858 (/home_ego/netbeans/exit/target/exit-0.0.1-SNAPSHOT.jar started by ego in /home_ego/netbeans/exit)
2024-06-14T13:51:51.330+02:00  INFO 56858 --- [exit] [           main] de.idon.exit.ExitApplication             : No active profile set, falling back to 1 default profile: "default"
2024-06-14T13:51:52.046+02:00  INFO 56858 --- [exit] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2024-06-14T13:51:52.056+02:00  INFO 56858 --- [exit] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-06-14T13:51:52.056+02:00  INFO 56858 --- [exit] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.24]
2024-06-14T13:51:52.086+02:00  INFO 56858 --- [exit] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-06-14T13:51:52.087+02:00  INFO 56858 --- [exit] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 673 ms
2024-06-14T13:51:52.340+02:00  INFO 56858 --- [exit] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2024-06-14T13:51:52.350+02:00  INFO 56858 --- [exit] [           main] de.idon.exit.ExitApplication             : Started ExitApplication in 1.334 seconds (process running for 1.622)
2024-06-14T13:51:52.728+02:00  INFO 56858 --- [exit] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2024-06-14T13:51:52.729+02:00  INFO 56858 --- [exit] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2024-06-14T13:51:52.730+02:00  INFO 56858 --- [exit] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
2024-06-14T13:51:52.755+02:00  WARN 56858 --- [exit] [nio-8080-exec-1] org.apache.tomcat.util.net.NioEndpoint   : The executor associated with thread pool [http-nio-8080] has not fully shutdown. Some application threads may still be running.
I/O error on GET request for "http://localhost:8080": Connection reset
========================================
Application exited with status: 42
========================================
```

or

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.2.6)

2024-06-14T13:51:55.578+02:00  INFO 56915 --- [exit] [           main] de.idon.exit.ExitApplication             : Starting ExitApplication v0.0.1-SNAPSHOT using Java 17.0.11 with PID 56915 (/home_ego/netbeans/exit/target/exit-0.0.1-SNAPSHOT.jar started by ego in /home_ego/netbeans/exit)
2024-06-14T13:51:55.582+02:00  INFO 56915 --- [exit] [           main] de.idon.exit.ExitApplication             : No active profile set, falling back to 1 default profile: "default"
2024-06-14T13:51:56.277+02:00  INFO 56915 --- [exit] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port 8080 (http)
2024-06-14T13:51:56.286+02:00  INFO 56915 --- [exit] [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2024-06-14T13:51:56.286+02:00  INFO 56915 --- [exit] [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.24]
2024-06-14T13:51:56.316+02:00  INFO 56915 --- [exit] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2024-06-14T13:51:56.317+02:00  INFO 56915 --- [exit] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 663 ms
2024-06-14T13:51:56.576+02:00  INFO 56915 --- [exit] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2024-06-14T13:51:56.587+02:00  INFO 56915 --- [exit] [           main] de.idon.exit.ExitApplication             : Started ExitApplication in 1.326 seconds (process running for 1.63)
2024-06-14T13:51:56.954+02:00  INFO 56915 --- [exit] [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring DispatcherServlet 'dispatcherServlet'
2024-06-14T13:51:56.954+02:00  INFO 56915 --- [exit] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2024-06-14T13:51:56.955+02:00  INFO 56915 --- [exit] [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 1 ms
2024-06-14T13:51:56.978+02:00  WARN 56915 --- [exit] [nio-8080-exec-1] org.apache.tomcat.util.net.NioEndpoint   : The executor associated with thread pool [http-nio-8080] has not fully shutdown. Some application threads may still be running.
I/O error on GET request for "http://localhost:8080": Connection reset
========================================
Application exited with status: 0
====
WHY?
====
========================================
```
