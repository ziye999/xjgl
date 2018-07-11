var menuMap = new Map();
menuMap.put("000001","/module/zzjg/zkdw.js","组织单位");
menuMap.put("000002","/module/zzjg/ckdw.js","参考单位");
menuMap.put("00000201","/module/zzjg/njinfo.js","等级信息");
menuMap.put("00000202","/module/zzjg/bjinfo.js","课程信息");

//------------------基本信息------------------------------
menuMap.put("000101","/module/basicsinfo/building.js","考点信息");//考点信息
menuMap.put("000102","/module/basicsinfo/room.js","考场信息");//考场信息
menuMap.put("000103","/module/basicsinfo/teacher.js","监考信息");//监考信息

//------------------设置考试参数------------------------------
menuMap.put("000201","/module/exambasic/examround.js","考试轮次");//考试轮次
menuMap.put("000202","/module/basicsinfo/examsubject.js","设置考试科目");//设置考试科目

//------------------考生报名------------------------------=======
menuMap.put("000301","/module/registration/exam_registration.js","生成考生名单");//生成考生名单
menuMap.put("000302","/module/registration/examStuModify.js","修改参考考生");//修改参考考生
menuMap.put("000303","/module/registration/exam_review.js","考生名单审核");//考生名单审核
menuMap.put("00030301","/module/registration/exam_supplement.js","补报考生审核");//补报考生审核
menuMap.put("00030302","/module/registration/exam_delete.js","删除考生审核");//删除考生审核
menuMap.put("000304","/module/exambasic/examInfomation.js","查询考生信息");//查询考生信息
menuMap.put("00030401","/module/exambasic/examInfomation_show.js","查询考生信息详细");//查询考生信息详细
//menuMap.put("00030402","/module/exambasic/examInfomation_check.js","核对考生信息");//核对考生信息
menuMap.put("000305","/module/exambasic/examInfomationCount.js","报名信息统计");//报名信息统计

//------------------考试安排------------------------------
menuMap.put("000401","/module/examArrangement/examSiteSchool.js","设置考点单位");//设置考点单位
menuMap.put("000402","/module/examArrangement/examSchoolAnPai.js","参考单位安排");//参考单位安排
menuMap.put("00040201","/module/examArrangement/examSchoolArrange.js","参考单位安排");//参考单位安排
menuMap.put("000403","/module/examArrangement/examTimeArrange.js","考试时间安排");//考试时间安排
menuMap.put("000404","/module/examArrangement/examRoomArrange.js","考场安排");//考场安排
menuMap.put("000405","/module/examArrangement/examTeacherArrange.js","监考老师抽取");//监考老师抽取
menuMap.put("00040401","/module/examArrangement/subExamRoomArrange.js","安排考场");//考场安排子功能(考场安排)
menuMap.put("00040402","/module/examArrangement/showExamRoomArrange.js","考场分布");//考场安排子功能(考场安排)
//------------------考试安排数据打印------------------------------
menuMap.put("000501","/module/examArrangement/dataPrint.js","考试安排数据打印");//考试安排数据打印

//------------------考试成绩------------------------------
menuMap.put("000601","/module/examResults/resultsRegister.js","成绩登记");//成绩登记
menuMap.put("000602","/module/examResults/examUpdate.js","成绩修改");//成绩修改
menuMap.put("000604","/module/examResults/scoresQuery.js","成绩查询打印");//成绩查询打印

//------------------违纪处理------------------------------
menuMap.put("000701","/module/cheatStu/cheatStudent.js","录入违纪情况");//录入违纪情况
menuMap.put("000702","/module/cheatStu/lackOfTestStudent.js","录入缺考考生");//录入缺考考生
menuMap.put("000711","/module/cheatStu/auditCheatStudent.js","违纪情况审核");//违纪情况审核
menuMap.put("000703","/module/cheatStu/cheatConditionPrint.js","违纪情况打印");//违纪情况打印

//------------------成绩修正------------------------------
menuMap.put("000801","/module/scoreModify/modifyScore.js","修正成绩");//修正成绩
menuMap.put("00080101","/module/scoreModify/manualModifyScore.js","手动修正成绩");//手动修正成绩
menuMap.put("000802","/module/gradeAmendment/gradeAdtReview.js","成绩修正审核");//成绩修正审核
menuMap.put("00080201","/module/gradeAmendment/show_gradeAdtReview.js","成绩修正审核");//成绩修正审核

//------------------成绩统计补录------------------------------
menuMap.put("000901","/module/resultsStatisticalCollection/set_Standard.js","设置统计标准");//设置统计标准

//menuMap.put("00090201","/module/resultsStatisticalCollection/treePanel.js");//成绩统计
menuMap.put("000902","/module/resultsStatisticalCollection/resultsStatistical.js","成绩统计");//成绩统计
menuMap.put("000903","/module/resultsStatisticalCollection/resultsCollection.js","小题成绩补录");//小题成绩补录
menuMap.put("00090301","/module/resultsStatisticalCollection/shwoResults.js","小题成绩补录详细");

//------------------日常管理------------------------------
menuMap.put("100203","/module/dailyManage/generateSchoolCode.js","生成单位代码");//生成单位代码
menuMap.put("100206","/module/dailyManage/applyForStudy.js","不通过申请");//不通过申请
menuMap.put("100207","/module/dailyManage/breakStudy.js","不通过申请审核");//不通过申请审核
//照片替换
menuMap.put("100902","/module/dailyManage/photoUpdate.js","照片更新");//照片更新
menuMap.put("100903","/module/dailyManage/auditPhotoUpdate.js","照片更新审核");//照片更新审核

//------------------通过升级------------------------------
//menuMap.put("100306","/module/dailyManage/generateRollCode.js","生成学号辅号");//生成学号辅号
menuMap.put("100307","/module/academicGrade/ImpAcademicGrade.js","导入学业考试成绩");//导入学业考试成绩

//------------------报表打印------------------------------
menuMap.put("100603","/module/rollReport/studentRosterPrint.js","考生花名册打印");//考生花名册打印
menuMap.put("100604","/module/rollReport/rollCardPrint.js","考生卡打印");//考生卡打印
menuMap.put("100606","/module/rollReport/picPrint.js","照片核对打印");//照片核对打印
menuMap.put("100607","/module/reportPrint/studentNotPhotoInfo.js","无照片考生信息打印");//无照片考生信息打印
menuMap.put("100608","/module/reportPrint/graduationStuInfo.js","结业考生信息打印");//结业考生信息打印

//综合查询
menuMap.put("101001","/module/stuSearch/graduateStuInfo.js","通过考生名单");
menuMap.put("101002","/module/rollReport/certificatePrint.js","通过证书印发");

//系统管理
menuMap.put("101201","/module/userRole/userManager.js","用户管理");
menuMap.put("102001","/module/sysManagement/roleinfo.js","角色管理");//角色管理

menuMap.put("102005","/module/examineePrint/examineePrint.js","报名统计");
