--��ü�����ȸ
-- ���, ����(�̸�), �μ���, ��������, �Ի����� ��ȸ�ǰ�
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


-- ����� �ҼӵǾ� �ִ� �μ����� ��ȸ: �μ��ڵ�, �μ��� ��ȸ
-- IT �μ��� ���� ������� ��ȸ
select * from employees
where �μ��ڵ� = (IT�� �μ��ڵ�)
;

select department_id, department_name
from departments;

--private int department_id;

select distinct department_id, nvl(department_name, '�ҼӾ���') department_name
--select distinct nvl(to_char(department_id), '�ڵ����'), nvl(department_name, '�ҼӾ���') department_name
from employees e left outer join departments d using(department_id);




select last_name || ' ' || first_name name, job_title, department_name, e.*
from employees e inner join jobs j on e.job_id = j.job_id
left outer join departments d on d.department_id = e.department_id
where employee_id = 100;


--update_job_history Ʈ���� ��� �ߴ� ó�� enable/disable
alter trigger update_job_history disable;

-- �����ڷ� �ִ� �������
select distinct manager_id from employees order by 1;

select employee_id, last_name||' '||first_name name, manager_id
from employees
order by 1;

���, �����, �����ڻ��, �����ڻ���� ��ȸ
select e.employee_id, e.last_name||' '||e.first_name name
        , e.manager_id, m.last_name||' '||m.first_name manager_name
from employees e left outer join employees m on e.manager_id = m.employee_id
order by 1;

-- manager_id �÷��� employees �� employee_id �� reference �ϰ� �ִ�
-- FK�� constraint���� EMP_MANAGER_FK
-- reference �Ǵ� �θ����� �����ɶ� �÷��� ������ null �� ����
-- �����ִ� constraint�� ����
alter table employees drop constraint emp_manager_fk;
-- ���������� constraint�� ����
alter table employees add constraint emp_manager_fk 
        foreign key(manager_id) references employees(employee_id) on delete set null;

-- �����ִ� constraint�� ����
alter table job_history drop constraint JHIST_EMP_FK;
-- ���������� constraint�� ����
alter table job_history add constraint jhist_emp_fk 
            foreign key(employee_id) references employees(employee_id) on delete cascade;


-- �����ִ� constraint�� ����
alter table departments drop constraint DEPT_MGR_FK;
-- ���������� constraint�� ����
alter table departments add constraint DEPT_MGR_FK 
            foreign key(manager_id) references employees(employee_id) on delete set null;



select employees_seq.currval from dual;




update employees set salary = 10000;
rollback;


















