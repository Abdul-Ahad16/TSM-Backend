//package com.stg.tsm.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Tuple;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.JoinType;
//import javax.persistence.criteria.Order;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.stg.tsm.dto.UserstorySearchDTO;
//import com.stg.tsm.entity.SprintMaster;
//import com.stg.tsm.entity.SprintMaster_;
//import com.stg.tsm.entity.TaskMaster;
//import com.stg.tsm.entity.TaskMasterPK;
//import com.stg.tsm.entity.TaskMasterPK_;
//import com.stg.tsm.entity.TaskMaster_;
//import com.stg.tsm.entity.UserstoryMaster;
//import com.stg.tsm.entity.UserstoryMaster_;
//import com.stg.tsm.exception.TsmException;
//import com.stg.tsm.repos.ProjectAssignmentRepository;
//import com.stg.tsm.repos.TaskMasterRepository;
//import com.stg.tsm.repos.UserstoryMasterRepository;
//
//public class CriteriaBuilderClass {
//
//    @Autowired
//    private static EntityManager entityManager;
//
//
//    public static final String PART_NO_FIELD = "sprintName";
//    public static final String DESCRIPTION_FIELD = "applicationName";
//
//
//    public static List<Tuple> readAll(UserstorySearchDTO userstorySearchDTO) throws TsmException {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Tuple> criteriaQuery = cb.createQuery(Tuple.class);
//        Root<SprintMaster> sprintMaster = criteriaQuery.from(SprintMaster.class);
//        Join<SprintMaster, UserstoryMaster> userStoryMaster = sprintMaster.join(SprintMaster_.userstoryMasters,
//                JoinType.LEFT);
//        Join<UserstoryMaster, TaskMasterPK> taskMasterPk = userStoryMaster.join(UserstoryMaster_.TASK_MASTERS, JoinType.LEFT);
//        Join<TaskMasterPK, TaskMaster> taskMaster = taskMasterPk.join(TaskMasterPK_.TASK_ID, JoinType.LEFT);
//        Predicate assignmnetPreicate = cb.equal(taskMaster.get(TaskMasterPK_.ASSIGNMENT_ID),userstorySearchDTO.getAssignmnetId());
//
//        criteriaQuery
//                .multiselect(userStoryMaster.get(UserstoryMaster_.USERSTORY_ID), taskMaster.get(TaskMaster_.ID),
//                        userStoryMaster.get("userstoryName"), taskMaster.get("taskName"), taskMaster.get("efforts"),
//                        taskMaster.get("createdDate"), taskMaster.get(TaskMaster_.date))
//                .having(cb.notEqual(taskMaster.get("taskName"), null)).where(assignmnetPreicate).orderBy(applyOrder(userstorySearchDTO.getSortField(), sprintMaster, cb, userstorySearchDTO.getSortOrder())).distinct(true);
//        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
//        typedQuery.setFirstResult(userstorySearchDTO.getStartingRecordNumber());
//        typedQuery.setMaxResults(userstorySearchDTO.getPageSize());
//        return typedQuery.getResultList();
//    }
//
//    public Long countAll() {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Tuple> criteriaQuery = cb.createQuery(Tuple.class);
//        Root<SprintMaster> sprintMaster = criteriaQuery.from(SprintMaster.class);
//        Join<SprintMaster, UserstoryMaster> userStoryMaster = sprintMaster.join(SprintMaster_.userstoryMasters,
//                JoinType.LEFT);
//        Join<TaskMasterPK, TaskMaster> taskMaster = userStoryMaster.join(UserstoryMaster_.TASK_MASTERS, JoinType.LEFT);
//
//        Predicate taskmasterPredicate = cb.isNotNull(taskMaster.get(TaskMaster_.TASK_NAME));
//
//        criteriaQuery
//                .multiselect(userStoryMaster.get(UserstoryMaster_.USERSTORY_ID), taskMaster.get(TaskMaster_.ID),
//                        userStoryMaster.get("userstoryName"), taskMaster.get("taskName"), taskMaster.get("efforts"),
//                         taskMaster.get(TaskMaster_.date),taskMaster.get("createdDate"))
//                .where(taskmasterPredicate).having(cb.notEqual(taskMaster.get("taskName"), null)).distinct(true);
//        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
//        return (long) typedQuery.getResultList().size();
//    }
//
//    public int countSearch(int sprintId) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Tuple> criteriaQuery = cb.createQuery(Tuple.class);
//        Root<SprintMaster> sprintMaster = criteriaQuery.from(SprintMaster.class);
//        Join<SprintMaster, UserstoryMaster> userStoryMaster = sprintMaster.join(SprintMaster_.userstoryMasters,
//                JoinType.LEFT);
//        Join<TaskMasterPK, TaskMaster> taskMaster = userStoryMaster.join(UserstoryMaster_.TASK_MASTERS, JoinType.LEFT);
//
//        Predicate sprintIdPredicate = cb.equal(sprintMaster.get(SprintMaster_.SPRINT_ID), sprintId);
//
//        Predicate taskmasterPredicate = cb.isNotNull(taskMaster.get(TaskMaster_.TASK_NAME));
//
//        Predicate predicate = cb.and(sprintIdPredicate, taskmasterPredicate);
//
//        criteriaQuery
//                .multiselect(userStoryMaster.get(UserstoryMaster_.USERSTORY_ID), taskMaster.get(TaskMaster_.ID),
//                        userStoryMaster.get("userstoryName"), taskMaster.get("taskName"), taskMaster.get("efforts"),
//                         taskMaster.get(TaskMaster_.date),taskMaster.get("createdDate"))
//                .where(predicate).having(cb.notEqual(taskMaster.get("taskName"), null)).distinct(true);
//        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
//        return typedQuery.getResultList().size();
//    }
//
//    public List<Tuple> readUserstoryBySprintAndApplication(int sprintId, int offset, int limit) throws TsmException {
//
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Tuple> criteriaQuery = cb.createQuery(Tuple.class);
//        Root<SprintMaster> sprintMaster = criteriaQuery.from(SprintMaster.class);
//        Join<SprintMaster, UserstoryMaster> userStoryMaster = sprintMaster.join(SprintMaster_.userstoryMasters,
//                JoinType.LEFT);
//        Join<TaskMasterPK, TaskMaster> taskMaster = userStoryMaster.join(UserstoryMaster_.TASK_MASTERS, JoinType.LEFT);
//
//        List<Predicate> predicates = new ArrayList<>();
//
//        Predicate sprintIdPredicate = cb.equal(sprintMaster.get(SprintMaster_.SPRINT_ID), sprintId);
//
//        Predicate taskmasterPredicate = cb.isNotNull(taskMaster.get(TaskMaster_.TASK_NAME));
//
//        Predicate predicate = cb.and(sprintIdPredicate, taskmasterPredicate);
//
//        criteriaQuery
//                .multiselect(userStoryMaster.get(UserstoryMaster_.USERSTORY_ID), taskMaster.get(TaskMaster_.ID),
//                        userStoryMaster.get("userstoryName"), taskMaster.get("taskName"), taskMaster.get("efforts"),
//                         taskMaster.get(TaskMaster_.date),taskMaster.get("createdDate"))
//                .where(predicate).distinct(true);
//
//        TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
//        typedQuery.setFirstResult(offset);
//        typedQuery.setMaxResults(limit);
//        return typedQuery.getResultList();
//    }
//
//
//    public List<Tuple> taskList(UserstorySearchDTO searchDTO) throws TsmException {
//        return this.readUserstoryBySprintAndApplication(searchDTO.getSprintId(), searchDTO.getStartingRecordNumber(),
//                searchDTO.getPageSize());
//
//    }
//
//
//
//     public static List<Order> applyOrder(String sortField, Root<SprintMaster> iRoot, CriteriaBuilder cb, String sortOrder) {
//            List<Order> orders = new ArrayList<>();
//            String sorting = "";
//            switch (sortField) {
//                case "userstoryName":
//                    sorting = "userstoryName";
//                    break;
//                case "date":
//                    sorting = "date";
//                    break;
//                case "taskName":
//                    sorting = "taskName";
//                    break;
//                case "efforts":
//                    sorting = "efforts";
//                    break;
//                default:
//                    sorting = "userstoryName";
//            }
//            if (sortOrder == "asc") {
//                orders.add(cb.asc(iRoot.get(sorting)));
//            } else {
//                orders.add(cb.desc(iRoot.get(sorting)));
//            }
//            return orders;
//        }
//
//}
