com.watchserviceagent.watchservice_agent
├── collector                                       # [3단계] 파일 속성·지표 수집 모듈
│   ├── FileMetadataCollector.java                  # 파일 크기(size), 수정시간, 권한 등 기본 메타데이터 수집
│   ├── FileHashCalculator.java                     # 파일 내용 기반 해시값(MD5, SHA-256) 계산
│   ├── EntropyCalculator.java                      # 파일의 Shannon 엔트로피(무작위성) 계산
│   └── DepthCalculator.java                        # 감시 루트로부터 상대 경로 깊이(b-level) 계산
│
├── config                                          # 스프링 전역 설정 및 Bean 등록
│   ├── AppConfig.java                              # 공용 Bean, ThreadPool, 경로 설정 등 전역 환경 설정
│   └── DatabaseConfig.java                         # SQLite 또는 H2 DB 설정 (JPA 연결)
│
├── controller                                      # 외부 요청 진입점 (REST API, View Controller)
│   └── api
│       ├── HealthController.java                   # 서버 상태 확인 API (/health → "ok")
│       └── WatcherController.java                  # 감시 시작/중지 API (/watcher/start, /stop)
│
├── domain                                          # DB 매핑용 엔티티 클래스
│   ├── WatchEventEntity.java                       # 감시 이벤트(DB 저장용) 엔티티
│   └── FileInfoEntity.java                         # 파일 속성·지표(DB 저장용) 엔티티
│
├── dto                                             # 데이터 전송 객체 (Controller ↔ Service)
│   ├── common
│   │   └── WatchEventRecord.java                   # 감시 이벤트 DTO (JSON 직렬화 포함)
│   └── collector
│       └── FileInfoDTO.java                        # Collector로부터 수집된 파일 속성·지표 DTO
│
├── repository                                      # 데이터베이스 CRUD 계층 (Spring Data JPA)
│   ├── WatchEventRepository.java                   # 감시 이벤트 저장/검색 인터페이스
│   └── FileInfoRepository.java                     # 파일 정보 저장/검색 인터페이스
│
├── service                                         # 핵심 비즈니스 로직 계층
│   ├── watcher
│   │   └── WatcherService.java                     # 폴더 감시 핵심 로직 (WatchService + 스레드 + 재귀 감시)
│   ├── detector                                    # [4단계] 탐지 로직 패키지 (이상 동작 감지)
│   │   ├── ExtensionChangeDetector.java            # 파일 확장자 변경 탐지기
│   │   └── BulkChangeDetector.java                 # 일정 시간 내 다수 변경 감지기
│   └── notifier                                   # [5단계] 알림/이메일 전송 모듈
│       └── EmailNotifier.java                      # SMTP 기반 이메일 알림 발송기
│
└── util                                            # 공용 유틸리티 함수 모음
├── HashUtils.java                              # MD5, SHA-256 등 해시 계산 유틸
├── FileUtils.java                              # 파일 확장자/크기 변환 등 보조 함수
└── TimeUtils.java                              # Timestamp 포맷 변환, 시간 관련 도우미
