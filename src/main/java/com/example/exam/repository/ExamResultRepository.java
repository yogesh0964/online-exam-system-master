package com.example.exam.repository;

import com.example.exam.model.Exam;
import com.example.exam.model.ExamResult;
import com.example.exam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    List<ExamResult> findByExam(Exam exam);

    @Transactional
    void deleteByStudent_Id(Long studentId);

    List<ExamResult> findByStudent(User student);
    List<ExamResult> findByStudentOrderBySubmissionTimeDesc(User student);
    List<ExamResult> findTop5ByOrderBySubmissionTimeDesc();

    @Transactional
    void deleteByStudent(User student);

    // Exam-wise all results, highest score first (for ranking)
    List<ExamResult> findByExamOrderByScoreAchievedDesc(Exam exam);

    // Count kitne students ne zyada score kiya (rank = count + 1)
    @Query("SELECT COUNT(r) FROM ExamResult r WHERE r.exam = :exam AND r.scoreAchieved > :score")
    long countStudentsWithHigherScore(@Param("exam") Exam exam, @Param("score") int score);

    // Total students who attempted this exam
    @Query("SELECT COUNT(r) FROM ExamResult r WHERE r.exam = :exam")
    long countByExam(@Param("exam") Exam exam);
}