--�������� ����

create table notice(
    id      NUMBER constraint notice_id_pk primary key,
    title   VARCHAR2(300) NOT NULL,
    content VARCHAR2(4000) NOT NULL,
    writer  VARCHAR2(50) NOT NULL, -- �ۼ��� ���̵�
    writedate date default sysdate,
    readcnt NUMBER DEFAULT 0, -- ��ȸ��
    filename VARCHAR2(300),
    filepath VARCHAR2(600),
    root number, -- ��۰����� ���� id
    step number default 0, -- �� ����
    indent number default 0, -- �鿩����
    rid number constraint notice_rid_fk references notice(id) on delete cascade,
    constraint notice_writer_fk FOREIGN KEY(writer)
        REFERENCES member(userid)
);

alter table notice add (
rid number constraint notice_rid_fk references notice(id) on delete cascade
);

commit;


--ȸ�� Ż�� ��?
--1. writer�� null �ֱ� ==> writer �÷��� null�� ����ؾ��ϰ�
--   constraint�� �ɼ� ���� : on delete set null
--2. Ż���ڰ� �� ���� ���� ==> writer �÷��� null�� ������� �ʰ�
--   constraint�� �ɼ� ���� : on delete set cascade

commit;

--PK�� id �÷��� ������ ������ ����
create sequence seq_notice
start with 1 increment by 1 nocache;

--notice�� PK�� id�� sequence�� �ڵ� ���� ��ų Ʈ���� ����
create or replace trigger trg_notice
    before insert on notice
    for each row
begin
    select seq_notice.nextval into :new.id from dual;
    if( :new.root is null) then
--    ������ ��� root�� ���� �ֱ� ���� ó��
    select seq_notice.currval into :new.root from dual;
--    else 
--    --����� ��� ������ ���� step����ó��
--        update notice set step = step + 1 where root = :old.root and step > :old.step;
end if;
end;
/
commit;


insert into notice (id, title, content, writer) values (seq_notice.nextval, '�׽�Ʈ ������', '����', 'ȫ�浿');
insert into notice (title, content, writer) values ('�׽�Ʈ ������2', '����2', 'ȫ�浿');

commit;

select name, userid, admin from member;

select * from notice;

update notice
set writer = case when id = '3' then 'admin' else 'admin2' end;

commit;

insert into notice(title, content, writer)
select title, content, writer from notice;

-- ������ ���� ��ȸ�ؿ� ������ �ش��ϴ� �÷� : rownum
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
    writer  VARCHAR2(50) NOT NULL, -- �ۼ��� ���̵�
    writedate date default sysdate,
    readcnt NUMBER DEFAULT 0 -- ��ȸ��
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

--���Ͽ� ÷���ϴ� ���� ����
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

--������ ������ ��۵� ����ó��
create or replace trigger trg_notice_delete
after delete on notice
for each row
begin
--������ ���� root�� ���� root�� ������ ���� ����
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