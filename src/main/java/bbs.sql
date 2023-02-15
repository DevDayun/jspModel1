
drop table bbs;

-- 시퀀스sequence란 순차적으로 증가하는 순번을 생성해 
-- 중복되지 않는 정숫값을 반환하는 데이터베이스 객체입니다.
create table bbs(
	seq int auto_increment primary key,
	id varchar(50) not null,
	
	ref decimal(8) not null,
	step decimal(8) not null,
	depth decimal(8) not null,
	
	title varchar(200) not null,
	content varchar(4000) not null,
	wdate timestamp not null,
	
	del decimal(1) not null,
	readcount decimal(8) not null
);

-- member 테이블의 id 컬럼과 board 테이블의 id 컬럼은 외래키foreign key로 엮어 있습니다. 
-- 만약 member 테이블에 없는 아이디로 게시물을 작성하려 하면 제약조건 위배로 에러가 날 것입니다. 
alter table bbs
add foreign key(id) references member(id);

insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount)
values('id', 
		(select ifnull(max(ref),0)+1 from bbs b), 0, 0,
		'title','content', now(), 0, 0);

select * from bbs;
delete from bbs where seq = 28;

select seq, id, ref, step, title, content, wdate, del, readcount
from
(select row_number()over(order by ref desc, step asc) as rnum,
	seq, id, ref, step, title, content, wdate, del, readcount
from bbs
-- 검색
order by ref desc, step asc) a
where rnum between 1 and 10;

-- 순서 : 1. update(최신 답글이 가장 위로 오도록 아래 답글들 밀어주기)
-- 2. insert(최신 답글 목록에 추가)
-- 조건 2가지 : 1. ref(그룹 번호)가 같고 2. step(행 번호)가 더 큰 글
update bbs
set step=step+1
where ref=(select ref from (select ref from bbs where seq=?) A)
	and step>(select step from (select step from bbs where seq=?) B);
	
insert into bbs(seq, id, ref, step, depth, title, content, wdate, del, readcount)
values(?, 
				(select ref from bbs where seq=?),
				(select step from bbs where seq=?) + 1,
				(select depth from bbs where seq=?) + 1,
				?, ?, now(), 0, 0)
				
