 CREATE TABLE depto(
  deptnum INTEGER PRIMARY KEY,
  name  VARCHAR(15),
  loc  VARCHAR(15),
  nemp  INTEGER);
 
  
 CREATE TABLE employee(
  empnum INTEGER PRIMARY KEY,
  name  VARCHAR(15) NOT NULL,
  job  VARCHAR(10),
  sal  INTEGER,
  datemaj VARCHAR(10),
  namemaj VARCHAR(15),
  deptnum INTEGER);
 
  
 INSERT INTO depto VALUES( 10, 'ACCOUNTING', 'grenoble', 0 );
 INSERT INTO depto VALUES( 20, 'RESEARCH',   'paris', 0   );
 INSERT INTO depto VALUES( 30, 'SALES',      'lyon', 0  );
 INSERT INTO depto VALUES( 40, 'OPERATIONS', 'lyon', 0   );
 
  
 INSERT INTO employee VALUES( 7839, 'KING',   'PRESIDENT', null, '17-NOV-81', 'KING', 10 );
 INSERT INTO employee VALUES( 7782, 'CLARK',  'MANAGER',   7839, '09-JUN-81', 'KING', 10 );
 INSERT INTO employee VALUES( 7934, 'MILLER', 'CLERK',     7782, '12-ENE-83', 'KING', 10 );
 INSERT INTO employee VALUES( 7566, 'JONES',  'MANAGER',   7839, '02-ABR-81', 'KING', 20 );
 INSERT INTO employee VALUES( 7902, 'FORD',   'ANALYST',   7566, '03-DIC-81', 'KING', 20 );
 INSERT INTO employee VALUES( 7369, 'SMITH',  'CLERK',     7902, '17-DIC-80', 'KING', 20 );
 INSERT INTO employee VALUES( 7788, 'SCOTT',  'ANALYST',   7566, '05-NOV-82', 'KING', 20 );
 INSERT INTO employee VALUES( 7876, 'ADAMS',  'CLERK',     7788, '09-DIC-82', 'KING', 20 );
 INSERT INTO employee VALUES( 7698, 'BLAKE',  'MANAGER',   7839, '01-MAY-81', 'KING', 30 );
 INSERT INTO employee VALUES( 7499, 'ALLEN',  'SALESMAN',  7698, '20-FEB-81', 'KING', 30 );
 INSERT INTO employee VALUES( 7654, 'MARTIN', 'SALESMAN',  7698, '28-SEP-81', 'KING', 30 );
 INSERT INTO employee VALUES( 7900, 'JAMES',  'CLERK',     7698, '03-DIC-81', 'KING', 30 );
 INSERT INTO employee VALUES( 7844, 'TURNER', 'SALESMAN',  7698, '08-SEP-81', 'KING', 30 );
 INSERT INTO employee VALUES( 7521, 'WARD',   'SALESMAN',  7698, '22-FEB-81', 'KING', 30 );
 
  
 UPDATE depto SET nemp = 3 WHERE deptnum = 10;
 UPDATE depto SET nemp = 5 WHERE deptnum = 20;
 UPDATE depto SET nemp = 6 WHERE deptnum = 30;
 UPDATE depto SET nemp = 0 WHERE deptnum = 40;
 
 
 
