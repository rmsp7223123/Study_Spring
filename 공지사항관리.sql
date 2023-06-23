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
    constraint notice_writer_fk FOREIGN KEY(writer)
        REFERENCES member(userid)
);

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
end;


insert into notice (id, title, content, writer) values (seq_notice.nextval, '�׽�Ʈ ������', '����', 'ȫ�浿');
insert into notice (title, content, writer) values ('�׽�Ʈ ������2', '����2', 'ȫ�浿');

commit;