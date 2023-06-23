select * from customer;

-- 시퀀셜한 숫자값을 자동으로 만들어내는 기능을 가진 객체: sequence
create sequence seq_customer
start with 8 increment by 1 nocache;

insert into customer(id, name)
values ( seq_customer.nextval , '박문수' );

-- 시퀀스 자동적용시키는 기능을 처리할 객체: trigger
create or replace trigger trg_customer
    before insert on customer
    for each row
begin
    select seq_customer.nextval into :new.id from dual;
end;
/


insert into customer( name)
values ( '심청' );
commit;

select * from customer;


