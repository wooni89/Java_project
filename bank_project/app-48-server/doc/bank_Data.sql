-- 고객 데이터 입력
INSERT INTO bank_customer(customer_no ,name, email, password, gender, address, credit_rating)
VALUES 
(1, '이강인', 'lee@psg.com', SHA1('1111'), 'M',  '주소1', 5),
(2, '손흥민', 'son@tot.com', SHA1('1111'), 'F',  '주소2', 4),
(3, '김민재', 'kim@byr.com', SHA1('1111'), 'M','주소3' , 3),
(4, '날강두','nalgangdoo@thief.com',SHA1('1111'),'F', '주소4', 2),
(5, '테스트 계정','aaa@test.com',SHA1('1111'),'M', '주소5', 3);

-- 계좌 데이터 입력
INSERT INTO bank_account(acc_no, account_num, password, owner, balance)
VALUES 
(1,'11101001', SHA1('1111'), 1, 10000),
(2,'11101002', SHA1('1111'), 2, 20000),
(3,'11101003', SHA1('1111'), 3, 30000),
(4,'11101004', SHA1('1111'), 4, 40000),
(5,'11101005', SHA1('1111'), 5, 50000);

-- 거래내역 데이터 입력 
INSERT INTO bank_transaction(tsac_no, acc_num, created_date, trade, amount, customer)
VALUES 
(1,'1', CURDATE(), '입금' ,1000 ,'이강인'),
(2,'2', CURDATE(), '출금' ,2000 ,'손흥민'),
(3,'3', CURDATE(), '입금' ,3000 ,'김민재'),
(4,'4' ,CURDATE(), '출금' ,4000 ,'이강인'),
(5,'5' ,CURDATE(), '입금' ,5000 ,'퍼거슨');

-- board 테스트 데이터
insert into bank_board(board_no, title, content, writer, category)
  values(11, '제목1', '내용', 1, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(12, '제목2', '내용', 1, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(13, '제목3', '내용', 3, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(14, '제목4', '내용', 4, 1);
insert into bank_board(board_no, title, content, writer, category)
  values(15, '제목5', '내용', 5, 2);
insert into bank_board(board_no, title, content, writer, category)
  values(16, '제목6', '내용', 5, 2);
insert into bank_board(board_no, title, content, writer, category)
  values(17, '제목7', '내용', 5, 2);
  
  -- board_category 테이블 예제 데이터
insert into board_category(board_category_no, name) values(1, '공지사항');
insert into board_category(board_category_no, name) values(2, '고객VOC');
