package sample.mybatis.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import sample.mybatis.domain.Student;

/**
 *
 * @author hengyunabc 2017-12-14
 *
 */
@Component
@Transactional
public class StudentDao {

	@Autowired
	public SqlSession sqlSession;

	public Student selectStudentById(long id) {
		return sqlSession.selectOne("selectStudentById", id);
	}

	public final Student finalSelectStudentById(long id) {
		return sqlSession.selectOne("selectStudentById", id);
	}

	public SqlSession getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
}
