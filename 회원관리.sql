CREATE TABLE member (
    name    VARCHAR2(50) NOT NULL,
    userid  VARCHAR2(50)
        CONSTRAINT member_userid_pk PRIMARY KEY,
    userpw  VARCHAR2(300) NOT NULL,
    gender  VARCHAR2(3) DEFAULT '남' NOT NULL,
    email   VARCHAR2(300) NOT NULL,
    profile VARCHAR2(300),
    birth   DATE,
    phone   VARCHAR2(20),
    post    VARCHAR2(5),
    address VARCHAR2(300),
    admin   VARCHAR2(2) DEFAULT 'N' NOT NULL --관리자여부(Y/N)
);
