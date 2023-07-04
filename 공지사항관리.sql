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
    root number, -- 답글관리를 위한 id
    step number default 0, -- 글 순서
    indent number default 0, -- 들여쓰기
    rid number constraint notice_rid_fk references notice(id) on delete cascade,
    constraint notice_writer_fk FOREIGN KEY(writer)
        REFERENCES member(userid)
);

alter table notice add (
rid number constraint notice_rid_fk references notice(id) on delete cascade
);

commit;


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
    if( :new.root is null) then
--    원글인 경우 root에 값을 넣기 위한 처리
    select seq_notice.currval into :new.root from dual;
--    else 
--    --답글인 경우 순서를 위한 step변경처리
--        update notice set step = step + 1 where root = :old.root and step > :old.step;
end if;
end;
/
commit;


insert into notice (id, title, content, writer) values (seq_notice.nextval, '테스트 공지글', '내용', '홍길동');
insert into notice (title, content, writer) values ('테스트 공지글2', '내용2', '홍길동');

commit;

select name, userid, admin from member;

select * from notice;

update notice
set writer = case when id = '3' then 'admin' else 'admin2' end;

commit;

insert into notice(title, content, writer)
select title, content, writer from notice;

-- 데이터 행을 조회해온 순서에 해당하는 컬럼 : rownum
select rownum, n.* from (select n.*, m.name from notice n
		inner join member m on n.writer = m.userid order by id desc)n order by 1 desc; 

select row_number() over(order by id) no, n.*, name 
from notice n inner join member m on n.writer = m.userid order by 1 desc;

commit;

alter table notice add (
root number,
step number default 0,
indent number default 0
);

select * from notice order by 1 desc;

update notice set root = id;

create table board(
    id      NUMBER constraint board_id_pk primary key,
    title   VARCHAR2(300) NOT NULL,
    content VARCHAR2(4000) NOT NULL,
    writer  VARCHAR2(50) NOT NULL, -- 작성자 아이디
    writedate date default sysdate,
    readcnt NUMBER DEFAULT 0 -- 조회수
);

create SEQUENCE seq_board start with 1 increment by 1 nocache;

create or replace trigger trg_board
before insert on board
for each row
begin
select seq_board.nextval into :new.id from dual;
end;
/
commit;

--방명록에 첨부하는 파일 관리
create table board_file(
id      NUMBER constraint board_file_id_pk primary KEY,
board_id number constraint board_file_fk references board(id) on delete cascade, 
filename varchar2(300),
filepath varchar2(600)
);

create sequence seq_board_file start with 1 increment by 1 nocache;

commit;

create or replace trigger trg_board_file
before insert on board_file
for each row
begin
select seq_board_file.nextval into :new.id from dual;
end;
/
commit;

--원글을 삭제시 답글들 삭제처리
create or replace trigger trg_notice_delete
after delete on notice
for each row
begin
--삭제한 글의 root와 같은 root인 데이터 행을 삭제
delete from notice where root = :old.root;
end;
/

alter trigger trg_notice_delete disable;
commit;

insert into board(title, content, writer) select title, content, writer from board;
commit;


select (select count(*) from board_file where b.id = board_id) filecnt, b.* from
		(select row_number()
		over(order by
		id) no, b.*, name from board b
		inner
		join member m on
		b.writer = m.userid) b
		order by no desc;
        
select * from notice order by id desc;