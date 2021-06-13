package com.edu.hutech.services.implementation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.edu.hutech.dtos.CourseDto;
import com.edu.hutech.entities.*;
import com.edu.hutech.functiondto.CourseSearchDto;
import com.edu.hutech.repositories.CourseRepository;
import com.edu.hutech.repositories.TrainingObjectiveRepository;
import com.edu.hutech.services.core.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager manager;

    @Autowired
    TrainingObjectiveRepository trainingObjectiveRepository;

    @Autowired
    TraineeCourseService traineeCourseService;

    @Autowired
    UserServiceImpl userService;

    @Override
    public void save(Course t) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate startDt = LocalDate.parse(t.getOpenDate(), formatter1);
        LocalDate endDt = LocalDate.parse(t.getEndDate(), formatter1);
        if (startDt.isBefore(LocalDate.now()) && endDt.isAfter(LocalDate.now())) {
            t.setStatusProgress("RUNNING");
        }

        if (startDt.isBefore(LocalDate.now()) && endDt.isBefore(LocalDate.now())) {
            t.setStatusProgress("FINISHED");
        }

        if (startDt.isAfter(LocalDate.now())) {
            t.setStatusProgress("WAITING");
        }

        t.setEndDate(endDt.format(formatter2));
        t.setOpenDate(startDt.format(formatter2));
        courseRepository.save(t);
    }

    @Override
    public void update(Course t) {
        courseRepository.save(t);
    }

    @Override
    public void delete(Integer id) {
        // TODO Auto-generated method stub

    }

    @Override
    public Course findById(Integer id) {
        Course course = courseRepository.getOne(id);
        List<TraineeCourse> traineeCourseList = new ArrayList<>();
        for (TraineeCourse traineeCourse : course.getTraineeCourses()) {
            if (traineeCourse.getDelFlag() == 0) {
                traineeCourseList.add(traineeCourse);
            }
        }
        course.setTraineeCourses(traineeCourseList);
        return course;
    }

    @Override
    public List<Course> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<CourseDto> searchByDto(CourseSearchDto dto, Integer trainerId, Integer traineeId) {
        if (dto == null) {
            return null;
        }
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = " where (1=1) AND co.delFlag = 0 ";
        String orderBy = " ";
        String sqlCount = "select count(co.id) from Course as co ";
        String sql = "select new com.edu.hutech.dtos.CourseDto(co) from Course as co ";

        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            sqlCount += " JOIN co.trainer as tra";
            sql += " JOIN co.trainer as tra";
            whereClause += " AND (co.name LIKE :text " + "OR tra.name LIKE :text) ";
        }

        sql += whereClause + orderBy;
        sqlCount += whereClause;
        Query q = manager.createQuery(sql, CourseDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
            q.setParameter("text", '%' + dto.getText().trim() + '%');
            qCount.setParameter("text", '%' + dto.getText().trim() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<CourseDto> entities = q.getResultList();
        Integer x = 0;

        if (entities.size() > 0) {
            if (trainerId != null) {
                User user = userService.findById(trainerId);
                entities.removeIf(courseDto -> !courseDto.getTrainerId().equals(user.getTrainer().getId()));
            }
        }

        if (traineeId != null) {
            Iterator<CourseDto> it = entities.iterator();
            User user = userService.findById(traineeId);
            while (it.hasNext()){
                CourseDto courseDto = it.next();
                if(courseDto != null){
                    TraineeCourse traineeCourse = traineeCourseService.getByTCourseIdAndTraineeId(courseDto.getId(), user.getTrainee().getId());
                    if (traineeCourse == null ) {
                        it.remove();
                    }
                }else {
                    break;
                }
            }
        }

        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return new PageImpl<>(entities, pageable, count);
    }

    public List<TraineeSubject> findSubjectByCourseIdAndTraineeId(Integer courseId, Integer traineeId) {
        String sql = "select * from trainee_subject ts where ts.course_id = " + courseId + " and ts.trainee_id = " + traineeId;
        Query query = manager.createNativeQuery(sql, TraineeSubject.class);
        return query.getResultList();
    }

}
