--공지사항 관리

create table notice(
    id      NUMBER constraint notice_id_pk primary key,
    title   VARCHAR2(300) NOT NULL,
    content VARCHAR2(4000) NOT NULL,
    writer  VARCHAR2(50) NOT NULL, -- 작성자 아이디
    writedate date default sysdate,
    readcnt NUMBER DEFAULT 0, -- 조회수
    filename VARCHAR2(300),
    filepath VARCHAR2(600),
    constraint notice_writer_fk FOREIGN KEY(writer)
        REFERENCES member(userid)
);

--회원 탈퇴 시?
--1. writer에 null 넣기 ==> writer 컬럼은 null을 허용해야하고
--   constraint에 옵션 지정 : on delete set null
--2. 탈퇴자가 쓴 글은 삭제 ==> writer 컬럼은 null을 허용하지 않고
--   constraint에 옵션 지정 : on delete set cascade

commit;

--PK인 id 컬럼에 적용할 시퀀스 생성
create sequence seq_notice
start with 1 increment by 1 nocache;

--notice의 PK인 id에 sequence를 자동 적용 시킬 트리거 생성
create or replace trigger trg_notice
    before insert on notice
    for each row
begin
    select seq_notice.nextval into :new.id from dual;
end;


insert into notice (id, title, content, writer) values (seq_notice.nextval, '테스트 공지글', '내용', '홍길동');
insert into notice (title, content, writer) values ('테스트 공지글2', '내용2', '홍길동');

commit;