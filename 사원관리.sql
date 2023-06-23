--전체사원조회
-- 사번, 성명(이름), 부서명, 업무제목, 입사일자 조회되게
select employee_id, last_name||' '||first_name name, e.department_id, department_name
        , e.job_id,job_title, hire_date
from employees e, departments d, jobs j
where e.department_id = d.department_id(+)
and e.job_id = j.job_id
order by employee_id;

select employee_id, last_name||' '||first_name name, e.department_id, department_name
        , job_id,job_title, hire_date
from departments d right outer join employees e on e.department_id=d.department_id
inner join jobs j using(job_id)
where e.department_id is null
--where ?
order by employee_id;


select employee_id, last_name, department_id from employees;


-- 사원이 소속되어 있는 부서정보 조회: 부서코드, 부서명 조회
-- IT 부서에 속한 사원정보 조회
select * from employees
where 부서코드 = (IT의 부서코드)
;

select department_id, department_name
from departments;

--private int department_id;

select distinct department_id, nvl(department_name, '소속없음') department_name
--select distinct nvl(to_char(department_id), '코드없음'), nvl(department_name, '소속없음') department_name
from employees e left outer join departments d using(department_id);




select last_name || ' ' || first_name name, job_title, department_name, e.*
from employees e inner join jobs j on e.job_id = j.job_id
left outer join departments d on d.department_id = e.department_id
where employee_id = 100;


--update_job_history 트리거 사용 중단 처리 enable/disable
alter trigger update_job_history disable;

-- 관리자로 있는 사원정보
select distinct manager_id from employees order by 1;

select employee_id, last_name||' '||first_name name, manager_id
from employees
order by 1;

사번, 사원명, 관리자사번, 관리자사원명 조회
select e.employee_id, e.last_name||' '||e.first_name name
        , e.manager_id, m.last_name||' '||m.first_name manager_name
from employees e left outer join employees m on e.manager_id = m.employee_id
order by 1;

-- manager_id 컬럼은 employees 의 employee_id 를 reference 하고 있다
-- FK인 constraint명이 EMP_MANAGER_FK
-- reference 되는 부모행이 삭제될때 컬럼의 정보에 null 로 변경
-- 원래있던 constraint를 삭제
alter table employees drop constraint emp_manager_fk;
-- 조건지정한 constraint를 지정
alter table employees add constraint emp_manager_fk 
        foreign key(manager_id) references employees(employee_id) on delete set null;

-- 원래있던 constraint를 삭제
alter table job_history drop constraint JHIST_EMP_FK;
-- 조건지정한 constraint를 지정
alter table job_history add constraint jhist_emp_fk 
            foreign key(employee_id) references employees(employee_id) on delete cascade;


-- 원래있던 constraint를 삭제
alter table departments drop constraint DEPT_MGR_FK;
-- 조건지정한 constraint를 지정
alter table departments add constraint DEPT_MGR_FK 
            foreign key(manager_id) references employees(employee_id) on delete set null;



select employees_seq.currval from dual;




update employees set salary = 10000;
rollback;


















