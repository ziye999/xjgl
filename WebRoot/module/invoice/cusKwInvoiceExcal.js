var lcid = "";
Ext.extend(system.application.baseClass,{
    /** 初始化 **/
    init: function(){
        this.initComponent();
        this.initListener();
        this.initFace();
        this.initQueryDate();
    },
    /** 初始化页面、内存等基本数据 **/
    initDate:function(){

    },
    /** 对组件设置监听 **/
    initListener:function(){
        //单元格点击事件，控制按钮的禁用情况
        var thiz = this;
        this.maingrid.on("cellclick",function(grid,rowIndex,columnIndex, e){
            var selected = thiz.maingrid.getStore().getAt(rowIndex);
            lcid = selected.get("LCID");
            if(columnIndex == 600){
                var newStore = new Ext.data.JsonStore({
                    autoLoad:false,
                    url:'dropListAction_getKskmBykslc.do?params='+lcid,
                    fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kspc_find").clearValue();
                Ext.getCmp("kspc_find").store=newStore;
                newStore.reload();
                Ext.getCmp("kspc_find").bindStore(newStore);

                var newStore1 = new Ext.data.JsonStore({
                    autoLoad:false,
                    url:'dropListAction_kaoDianMc.do?params='+lcid,
                    fields:["CODEID","CODENAME"]
                });
                Ext.getCmp("kd_find").clearValue();
                Ext.getCmp("kc_find").clearValue();
                Ext.getCmp("kd_find").store=newStore1;
                Ext.getCmp("kc_find").store.removeAll();
                newStore1.reload();
                Ext.getCmp("kd_find").bindStore(newStore1);

                thiz.tabPanel.setActiveTab(thiz.examInfoPanel);
                thiz.examGrid.$load({"examinee.lcid":lcid});
            }
        },this);
    },
    initComponent :function(){

        this.invoiceForm = this.createInvoiceForm();
        this.invoiceWindow = this.createInvoiceWindow();
        this.invoiceWindow.add(this.invoiceForm);

        this.mainPanel = this.createMainPanel();
        this.Form1 = this.createForm1();
        this.window = this.createWindow1();
        this.window.add(this.Form1);
        this.chouQuPanel = this.createChouQuForm();
        this.examInfoPanel = this.createExamInfoPanel();
        this.examCheckPanel = this.createExamStudentCheckPanel();
        this.tabPanel = new Ext.TabPanel({
            activeTab: 0,
            headerStyle: 'display:none',
            border:false,
            items: [this.mainPanel,this.examInfoPanel,this.examCheckPanel,this.chouQuPanel]
        });
    },
    createChouQuForm:function(){
        var sfz		= new Ext.ux.form.TextField({name:"sfz",id:"sfz",width:180});
        var xm		= new Ext.ux.form.TextField({name:"xm",id:"name",width:180});
        var cx 			= new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.maingridExamStu1,scope:this});
        var cz 			= new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch1.getForm().reset()},scope:this});
        var zp = new Ext.form.ComboBox({
            id:'zp',
            mode: 'local',
            triggerAction: 'all',
            editable:false,
            width:160,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: ['value','text'],
                data: [['1', '全部'], ['2', '已上传'],['3', '未上传']]
            }),
            valueField: 'value',
            displayField: 'text'
        });
        this.mainsearch1 = new Ext.form.FormPanel({
            region: "north",
            height:120,
            items:[{
                layout:'form',
                xtype:'fieldset',
                style:'margin:10 10',
                title:'查询条件',
                items: [{
                    xtype:"panel",
                    layout:"table",
                    layoutConfig: {
                        columns: 4
                    },
                    baseCls:"table",
                    items:[
                        {html:"身份证件号：",baseCls:"label_right",width:170},
                        {items:[sfz],baseCls:"component",width:200},
                        {html:"姓名：",baseCls:"label_right",width:170},
                        {items:[xm],baseCls:"component",width:200},
                        {html:"是否上传照片：",baseCls:"label_right",width:170},
                        {items:[zp],baseCls:"component",width:200},
                        {layout:"absolute", items:[cx,cz],baseCls:"component_btn",width:170}
                    ]
                }]
            }]
        })

        //选择轮次点击抽取后的主页面

//		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
        var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});

        var cm = [sm,
            {header: "组考单位",   	align:"center", sortable:true, dataIndex:"SSZGJYXZMC"},
            {header: "参考单位",   	align:"center", sortable:true, dataIndex:"XXMC"},
            {header: "姓名",   	align:"center", sortable:true, dataIndex:"XM"},
            {header: "身份证号",   	align:"center", sortable:true, dataIndex:"SFZJH"},
            {header: "性别",   	align:"center", sortable:true, dataIndex:"xbm"},
            {header: "民族",   	align:"center", sortable:true, dataIndex:"MZM"},
            {header: "文化程度",   	align:"center", sortable:true, dataIndex:"WHCD"},
            {header: "政治面貌",   	align:"center", sortable:true, dataIndex:"ZZMM"},
            {header: "专业",   	align:"center", sortable:true, dataIndex:"ZY"},
            {header: "职务",   	align:"center", sortable:true, dataIndex:"ZW"},
            {header: "执法类别",   	align:"center", sortable:true, dataIndex:"zflx"},
            {header: "执法范围",   	align:"center", sortable:true, dataIndex:"zffw"},
            {header: "发证机关",   	align:"center", sortable:true, dataIndex:"fzdw"},
            {header: "备注",   	align:"center", sortable:true, dataIndex:"remark"},
        ];

        this.grid2 = new Ext.ux.GridPanel({
            cm:cm,
            sm:sm,
            title:'考生信息表',
            excel:true,
            page:true,
            excel:true,
            rowNumber:true,
            tbar:[

                "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui1,scope:this}
                ,"->",{xtype:"button",text:"查看",id:"ck",iconCls:"p-icons-update",handler:this.selectSFZJH,scope:this}

            ],
            region:"center",
            action:'sign_getPage.do',
            fields:["XS_JBXX_ID","SSZGJYXZMC","XM","xbm","SFZJH","XXMC","MZM","ZZMM","ZY","ZW","WHCD","zflx","zffw","fzdw","remark","bmlcid","dm"],
            border:false
        });
        return new Ext.Panel({layout:"border",region:"center",items:[this.mainsearch1,this.grid2]})
    },
    //1
    createInvoiceForm:function(){
        var NAME = new Ext.form.TextField({id:"NAME",name:"NAME",maxLength:200, width:200});
        var DUTY = new Ext.form.TextField({id:"DUTY",name:"DUTY",maxLength:200, width:200});
        var ADDRESS = new Ext.form.TextField({id:"ADDRESS",name:"ADDRESS",maxLength:200, width:200});
        var TELLPHONE = new Ext.form.TextField({id:"TELLPHONE",name:"TELLPHONE",maxLength:200, width:200});
        var BANKNAME = new Ext.form.TextField({id:"BANKNAME",name:"BANKNAME",maxLength:200, width:200});
        var BANKNUM = new Ext.form.TextField({id:"BANKNUM",name:"BANKNUM",maxLength:200, width:200});
        var PHONE = new Ext.form.TextField({id:"PHONE",name:"PHONE",maxLength:200, width:200,regex:/^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\d{8}$/});
        var EMAIL = new Ext.form.TextField({id:"EMAIL",name:"EMAIL",maxLength:200, width:200,regex:/^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.com|.net|.cn$/});
        var REMARK = new Ext.form.TextField({id:"REMARK",name:"REMARK",maxLength:200, width:200});
        var NUMBER = new Ext.form.TextField({id:"NUMBER",name:"NUMBER",maxLength:200, width:200,regex:/^[1-9]\d*$/});

        var panel = new Ext.Panel({
            xtype:"panel",
            layout:"table",
            baseCls:"table",
            layoutConfig: {
                columns: 4
            },
            items:[
                {html:"<font class='required'>*</font>单位名称：",baseCls:"label_right",width:170},
                {items:[NAME],baseCls:"component",width:230},
                {html:"<font class='required'>*</font>税务号：",baseCls:"label_right",width:170},
                {items:[DUTY],baseCls:"component",width:230},
                {html:"<font class='required'>*</font>单位地址：",baseCls:"label_right",width:170},
                {items:[ADDRESS],baseCls:"component",width:230},
                {html:"<font class='required'>*</font>联系电话：",baseCls:"label_right",width:170},
                {items:[TELLPHONE],baseCls:"component",width:230},
                {html:"<font class='required'>*</font>电子邮箱：",baseCls:"label_right",width:170},
                {items:[EMAIL],baseCls:"component",width:230},
                {html:"开户行：",baseCls:"label_right",width:170},
                {items:[BANKNAME],baseCls:"component",width:230},
                {html:"银行账号：",baseCls:"label_right",width:170},
                {items:[BANKNUM],baseCls:"component",width:230},
                {html:"<font class='required'>*</font>推送手机：",baseCls:"label_right",width:170},
                {items:[PHONE],baseCls:"component",width:230},
                {html:"备注：",baseCls:"label_right",width:170},
                {items:[REMARK],baseCls:"component",width:230},
                {html:"<font class='required'>*</font>报名人数：",baseCls:"label_right",width:170},
                {items:[NUMBER],baseCls:"component",width:230},
            ]
        });


        return new Ext.ux.FormPanel({
            frame:false,
            defaults:{anchor:"100%"},
            items:[
                {layout:'form',  width:1000,xtype:"fieldset",title:"申请发票",items:[panel]},
            ]});
    },
    createInvoiceWindow:function(){
        var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.saveInvoice,scope:this});

        var cancel = new Ext.Button({text:"取消",iconCls:"p-icons-checkclose",handler:function(){this.invoiceWindow.hide()},scope:this});
        return new Ext.ux.Window({
            width:1000,
            height:300,
            tbar:{
                cls:"ext_tabr",
                items:[
                    "->",cancel
                    ,"->",save
                ]
            },
            listeners:{
                hide:function(){
                    this.invoiceForm.form.reset();
                },scope:this},
            items:[this.invoiceForm],
            title:"修改发票"});
    },
    createWindow1:function(){
//    	var save = new Ext.Button({text:"保存",iconCls:"p-icons-save",handler:this.addRound,scope:this});
        var cancel = new Ext.Button({text:"关闭",iconCls:"p-icons-checkclose",handler:function(){this.window.hide()},scope:this});
        return new Ext.ux.Window({
            width:1200,
            height:500,
            tbar:{
                cls:"ext_tabr",
                items:[
                    "->",cancel
                ]

            },
            listeners:{
                hide:function(){
                    this.Form1.form.reset();
                },scope:this},
            items:[this.Form1],
            title:"考生信息查看"});

    },
    createForm1:function(){
        var sfzjh2  = new Ext.form.TextField({name:"sfzjh",id:"sfzjh2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var xm2  = new Ext.form.TextField({name:"xm",id:"xm2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var xbm2  = new Ext.form.TextField({name:"xbm",id:"xbm2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var MZM2 = new Ext.form.TextField({name:"MZM",id:"MZM2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var WHCD2  = new Ext.form.TextField({name:"WHCD",id:"WHCD2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var ZZMM2  = new Ext.form.TextField({name:"ZZMM",id:"ZZMM2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var ZY2  = new Ext.form.TextField({name:"ZY",id:"ZY2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var XXMC2  = new Ext.form.TextField({name:"XXMC",id:"XXMC2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var ZW2  = new Ext.form.TextField({name:"ZW",id:"ZW2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var zflx2  = new Ext.form.TextField({name:"zflx",id:"zflx2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var zffw2  = new Ext.form.TextField({name:"zffw",id:"zffw2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var fzdw2  = new Ext.form.TextField({name:"fzdw",id:"fzdw2",maxLength:200, width:200,allowBlank:false,readOnly:true});
        var remark2 = new Ext.form.TextField({name:"remark",id:"remark2",maxLength:200, width:200,readOnly:true});
        var imagebox2 = new Ext.BoxComponent({autoEl: {tag:'img',src: 'img/basicsinfo/mrzp_img.jpg'},id:"box1",width:110,height:130});
        return new Ext.ux.FormPanel({
            frame:false,
            items:[{
                layout:'form',
                xtype:'fieldset',
                width:1090,
                style:'margin:10 10',
                title:'考生个人基础信息',
                items: [
                    {
                        xtype:"panel",
                        layout:"table",
                        baseCls:"table",
                        layoutConfig: {
                            columns: 4
                        },
                        baseCls:"table",
                        items:[
                            {html:"姓名：",baseCls:"label_right",width:212},
                            {items:[xm2],baseCls:"component",width:318},
                            {html:"照片：",baseCls:"label_right",width:212,rowspan:4},
                            {items:[imagebox2],baseCls:"component",width:318,rowspan:4},


                            {html:"身份证件号码：",baseCls:"label_right",width:212},
                            {items:[sfzjh2],baseCls:"component",width:318},
                            {html:"性别：",baseCls:"label_right",width:212},
                            {items:[xbm2],baseCls:"component",width:318},

                            {html:"民族：",baseCls:"label_right",width:212},
                            {items:[MZM2],baseCls:"component",width:318},

                            {html:"文化程度：",baseCls:"label_right",width:212},
                            {items:[WHCD2],baseCls:"component",width:318},

                            {html:"政治面貌：",baseCls:"label_right",width:212},
                            {items:[ZZMM2],baseCls:"component",width:318},
                            {html:"专业：",baseCls:"label_right",width:212},
                            {items:[ZY2],baseCls:"component",width:318},

                        ]
                    }]
            },

                {
                    layout:'form',
                    xtype:'fieldset',
                    width:1090,
                    style:'margin:10 10 10 10',
                    title:'考生个人考试信息',
                    items: [
                        {
                            xtype:"panel",
                            layout:"table",
                            baseCls:"table",
                            layoutConfig: {
                                columns: 4
                            },
                            baseCls:"table",
                            items:[
                                {html:"工作单位：",baseCls:"label_right",width:212},
                                {items:[XXMC2],baseCls:"component",width:318},
                                {html:"职务：",baseCls:"label_right",width:212},
                                {items:[ZW2],baseCls:"component",width:318},
                                {html:"执法类别：",baseCls:"label_right",width:212},
                                {items:[zflx2],baseCls:"component",width:318},
                                {html:"执法范围:",baseCls:"label_right",width:212},
                                {items:[zffw2],baseCls:"component",width:318},
                                {html:"发证机关：",baseCls:"label_right",width:212},
                                {items:[fzdw2],baseCls:"component",width:318},
                                {html:"备注：",baseCls:"label_right",width:212},
                                {items:[remark2],baseCls:"component",width:318},
                            ]
                        }]
                }
            ]
        })
    },
    createMainPanel:function() {
        //初始化搜索区
        // var xnxq	= new Ext.ux.form.XnxqField({ width:200,id:"infxnxq_find",readOnly:true});
        var xnxq1	= new Ext.ux.form.TextField({ width:180,id:"infxnxq_find1"});
        var xxkssj_find = 	new Ext.form.DateField({id:"xxkssj_find",editable:false,width:140,format:"Y-m-d"});
        var xxjssj_find = 	new Ext.form.DateField({id:"xxjssj_find",editable:false,width:140,format:"Y-m-d"});
        var zp = new Ext.form.ComboBox({
            id:'zp111',
            mode: 'local',
            triggerAction: 'all',
            editable:false,
            width:160,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: ['value','text'],
                data: [['1', '审核成功'], ['2', '审核中']]
            }),
            valueField: 'value',
            displayField: 'text'
        });
        var cx = new Ext.Button({x:17,y:-10,cls:"base_btn",text:"查询",handler:this.maingridExamStu,scope:this});
        var cz = new Ext.Button({x:87,y:-10,cls:"base_btn",text:"重置",handler:function(){this.mainsearch.getForm().reset()},scope:this});
        var dc = new Ext.Button({x:157,y:-10,cls:"base_btn",text:"导出",handler:this.exporInvoiceXls,scope:this});
        this.mainsearch = new Ext.form.FormPanel({
            region: "north",
            height:150,
            items:[{
                layout:'form',
                xtype:'fieldset',
                style:'margin:10 10',
                title:'查询条件',
                items: [{
                    xtype:"panel",
                    layout:"table",
                    layoutConfig: {
                        columns: 4
                    },
                    baseCls:"table",
                    items:[
                        // {html:"名称：",baseCls:"label_right",width:170},
                        // {items:[xnxq],baseCls:"component",width:200},
                        {html:"单位名称：",baseCls:"label_right",width:170},
                        {items:[xnxq1],baseCls:"component",width:200},
                        {html:"状态：",baseCls:"label_right",width:170},
                        {items:[zp],baseCls:"component",width:200},
                        {html:"开始日期：",baseCls:"label_right",width:170},
                        {items:[xxkssj_find],baseCls:"component",width:200},
                        {html:"结束日期：",baseCls:"label_right",width:170},
                        {items:[xxjssj_find],baseCls:"component",width:200},
                        {html:"",baseCls:"label_right",width:170},
                        {layout:"absolute", items:[cx,cz,dc],baseCls:"component_btn",width:240}
                    ]
                }]
            }]
        })

        //初始化数据列表区
        var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:true});
        var cm = [
            sm,
            //{header: "年度",   align:"center", sortable:true, dataIndex:"XN"},
            //{header: "季度",   align:"center", sortable:true, dataIndex:"XQ"},
            {header: "单位名称",   align:"center", sortable:true, dataIndex:"NAME"},
            {header: "税号",   align:"center", sortable:true, dataIndex:"DUTY"},
            {header: "地址",   align:"center", sortable:true, dataIndex:"ADDRESS"},
            {header: "电话",   align:"center", sortable:true, dataIndex:"TELLPHONE"},
            {header: "开户行",   align:"center", sortable:true, dataIndex:"BANKNAME"},
            {header: "银行账号",   align:"center", sortable:true, dataIndex:"BANKNUM"},
            {header: "推送手机",   align:"center", sortable:true, dataIndex:"PHONE"},
            {header: "邮箱",   align:"center", sortable:true, dataIndex:"EMAIL"},
            {header: "备注",   align:"center", sortable:true, dataIndex:"REMARK"},
            {header: "商品名称",   align:"center", sortable:true, dataIndex:"GOODSNAME"},
            {header: "税率",   align:"center", sortable:true, dataIndex:"RATE"},
            {header: "金额",   align:"center", sortable:true, dataIndex:"AMOUNT"},
            // {header: "未报名单位",   align:"center", sortable:true, dataIndex:"",
            //     renderer: function (data, metadata, record, rowIndex, columnIndex, store) {
            //         var xl = store.getAt(rowIndex).get('SL');
            //         return "<a href='#'>"+xl+"</a>";
            //     }
            // },
            {header: "数量",   align:"center", sortable:true, dataIndex:"NUMBER"},
            {header: "状态",   align:"center", sortable:true, dataIndex:"STATE"},
            {header: "日期",   align:"center", sortable:true, dataIndex:"DATESTART"}
        ];
        this.maingrid = new Ext.ux.GridPanel({
            cm:cm,
            sm:sm,
            tbar:{
                cls:"ext_tabr",
                items:[
                    "->",{xtype:"button",text:"修改发票",iconCls:"p-icons-update",handler:this.show,scope:this}
                    ,"->",{xtype:"button",text:"删除发票",id:"sc",iconCls:"p-icons-delete",handler:this.delete,scope:this}
                    ,"->",{xtype:"button",text:"取消申请",iconCls:"p-icons-delete",handler:this.cancel,scope:this}
                ]
            },
            title:'发票管理',
            page:true,
            rowNumber:true,
            region:"center",
            excel:true,
            action:"upload_getListPage.do",
            fields :["NAME","DUTY","ADDRESS","TELLPHONE","BANKNAME","BANKNUM","PHONE","EMAIL","REMARK","GOODSNAME","RATE","AMOUNT","NUMBER","ID","BMLCID","STATE","DATESTART"],
            border:false
        });
        return new Ext.Panel({layout:"border",region:"center",
            items:[this.mainsearch,this.maingrid]
        })
    },
    createExamInfoPanel:function(){
        var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
        var cm = [sm,
            {header: "单位名称",   align:"center", sortable:true, dataIndex:"examname"},
            {header: "报名人数",   align:"center", sortable:true, dataIndex:"sl"},
            {header: "是否缴费",   align:"center", sortable:true, dataIndex:"SHFLAG"}
        ];
        this.examGrid = new Ext.ux.GridPanel({
            cm:cm,
            sm:sm,
            tbar:[
                "->",{xtype:"button",text:"返回",iconCls:"p-icons-unpassing",handler:this.fanhui,scope:this}
                ,"->",{xtype:"button",text:"查看考生信息",id:"cq",iconCls:"p-icons-update",handler:this.addExamSection,scope:this}
            ],
            title:"报考单位信息列表",
            page:true,
            rowNumber:true,
            region:"center",
            excel:true,
            excelTitle:"报考单位信息列表",
            action:"statistics_getDetailedListPage.do",//teacher_getListPage.do
            fields :["examname","sl","SHFLAG","BMLCID"],
            border:false
        });
        //搜索区域
        var xmkhxjh_sel	= new Ext.ux.form.TextField({name:"xmkhxjh_sel",id:"xmkhxjh_sel",width:200});
        var mc = new Ext.form.ComboBox({
            id:'mc',
            mode: 'local',
            triggerAction: 'all',
            editable:false,
            width:150,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: ['value','text'],
                data: [['0', '已报名'],['1', '未报名']]
            }),
            valueField: 'value',
            displayField: 'text'
        });
        var kd = new Ext.form.ComboBox({
            id:'kd',
            mode: 'local',
            triggerAction: 'all',
            editable:false,
            width:150,
            store: new Ext.data.ArrayStore({
                id: 0,
                fields: ['value','text'],
                data: [['0', '已缴费'],['1', '未缴费']]
            }),
            valueField: 'value',
            displayField: 'text'
        });
        var kshd = new Ext.Button({x:10,y:-10,cls:"base_btn",id:"checkStudent",text:"考生核对",handler:this.checkExamInfomation,scope:this});
        var cx = new Ext.Button({x:10,y:-10,cls:"base_btn",text:"查询",handler:this.selectExamInfomation,scope:this});
        var cz = new Ext.Button({x:80,y:-10,cls:"base_btn",text:"重置",handler:function(){this.examSearch.getForm().reset()},scope:this});
        //var kssfz = new Ext.Button({x:220,y:-10,cls:"base_btn",id:"checkKssfz",text:"身份证验证",handler:this.checkSfz,width:90,scope:this});
        var kssfz = new Ext.Button({x:220,y:-10,cls:"base_btn",id:"checkKssfz",text:"修改身份证",handler:this.editsfz,width:90,scope:this});
        this.examSearch = new Ext.form.FormPanel({
            region: "north",
            height:120,
            items:[{
                layout:'form',
                xtype:'fieldset',
                style:'margin:10 10',
                title:'查询条件',
                items: [{
                    xtype:"panel",
                    layout:"table",
                    layoutConfig: {
                        columns: 7
                    },
                    baseCls:"table",
                    items:[
                        {html:"报名单位：",baseCls:"label_right",width:70},
                        {items:[xmkhxjh_sel],baseCls:"component",width:210},
                        {html:"报名状态：",baseCls:"label_right",width:70},
                        {items:[mc],baseCls:"component",width:170},
                        {html:"缴费状态：",baseCls:"label_right",width:70},
                        {items:[kd],baseCls:"component",width:170},
                        {layout:"absolute",items:[cx,cz],baseCls:"component_btn",width:160}
                    ]
                }]
            }]
        });
        return new Ext.Panel({layout:"border",region:"center",
            items:[this.examSearch,this.examGrid]
        })
    },
    cancel:function(){
        var thiz =this;
        var selected =  this.maingrid.getSelectionModel().getSelected();
        if(!selected){
            Ext.MessageBox.alert("消息","请选择一条记录！");
            return;
        }
        var selectedBuildings = this.maingrid.getSelectionModel().getSelections();
        var id =selectedBuildings[0].get("ID");
        var bmlcid = selectedBuildings[0].get("BMLCID");
        Ext.MessageBox.show({
            title:"消息",
            msg:"您确定要取消申请吗?",
            buttons:Ext.MessageBox.OKCANCEL,
            icon:Ext.MessageBox.QUESTION,
            fn:function(b){
                if(b=='ok'){
                    JH.$request({
                        params:{'orderNum':id,
                            'bmlcid':bmlcid,
                            'state':'1'
                        },
                        action:"upload_cancelInvoice.do",
                        handler:function(){
                            thiz.maingrid.$load();
                        }
                    })
                }
            }
        })
    },
    delete:function(){
        var thiz =this;
        var selected =  this.maingrid.getSelectionModel().getSelected();
        if(!selected){
            Ext.MessageBox.alert("消息","请选择一条记录！");
            return;
        }
        var selectedBuildings = this.maingrid.getSelectionModel().getSelections();
        var id =selectedBuildings[0].get("ID");
        var bmlcid = selectedBuildings[0].get("BMLCID");
        Ext.MessageBox.show({
            title:"消息",
            msg:"您确定要删除吗?",
            buttons:Ext.MessageBox.OKCANCEL,
            icon:Ext.MessageBox.QUESTION,
            fn:function(b){
                if(b=='ok'){
                    JH.$request({
                        params:{'orderNum':id,
                                'bmlcid':bmlcid,
                                'state':'1'
                        },
                        action:"upload_updateUse.do",
                        handler:function(){
                            thiz.maingrid.$load();
                        }
                    })
                }
            }
        })
    },
    exporInvoiceXls:function(){
        Ext.Msg.wait("正在导出","提示");
        var xxkssj=Ext.getCmp('xxkssj_find').getValue();
        var xxjssj=Ext.getCmp('xxjssj_find').getValue();
        if(xxkssj!=null && xxkssj!=''){
            if(xxjssj!=null && xxjssj!=''){
                if(xxkssj.getTime()>xxjssj.getTime()){
                    Ext.MessageBox.alert("错误","起始日期大于结束日期！");
                    return;
                }
            }else {
                Ext.MessageBox.alert("错误","结束日期不能为空！");
                return;
            }
        }else {
            if(xxjssj!=null && xxjssj!=''){
                Ext.MessageBox.alert("错误","开始日期不能为空！");
                return;
            }
        }
        var xnxq1 = Ext.getCmp('infxnxq_find1').getValue();
        var zp = Ext.getCmp('zp111').getValue();
        Ext.Ajax.request({
            url:"examineePrint_exporInvoiceXls.do",
            params:{
                'name':xnxq1,
                'state':zp,
                'dateTime':xxkssj,
                'endTime':xxjssj
            },
            scope:this,
            success: function (r, options) {
                var result =Ext.decode(r.responseText);
                if(result.success){
                    Ext.Msg.alert("提示","导出成功!");
                    window.open(Ext.get("ServerPath").dom.value+"/"+result.data.replace(/\\/g,"\/"));
                }else{
                    Ext.Msg.alert("提示",result.msg);
                }
            },
            failure: function (response, options) {
            }
        });
    },
    saveInvoice:function(){
        var selectedBuildings = this.maingrid.getSelectionModel().getSelections();
        var id =selectedBuildings[0].get("ID");
        var bmlcid = selectedBuildings[0].get("BMLCID");
        var thiz = this;
        thiz.invoiceForm.$submit({
            url:"upload_updateInvoice.do",
            params: {
                'orderNum':id,
                'bmlcid':bmlcid,
                'state':'1'
            },
            handler:function(form,result,thiz){
                thiz.invoiceWindow.hide();
                thiz.maingrid.$load();
            },
            scope:thiz
        });

    },
    //查看考生信息
    selectSFZJH:function(){
        var selected = this.grid2.getSelectionModel().getSelections();
        if(selected.length != 1){

            Ext.MessageBox.alert("消息","请选一个考生进行查看！");
            return;
        }
        var sfzjh = selected[0].get("SFZJH");
        this.window.show(null,function(){
            this.Form1.$load({
                params:{'sfzjh':sfzjh},
                action:"sign_getSFZ.do",
                handler:function(form,result,scope){

                    var url = "";
                    if(result.data.PATH==undefined){
                        url = "img/basicsinfo/mrzp_img.jpg";
                    }else{
                        url = "uploadFile/photo/"+result.data.PATH;

                    }
                    Ext.getCmp("box1").getEl().dom.src = url;
                }

            });
        },this)
    },
    addExamSection:function(){
        //主页面根据轮次进行数据抽取
        var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
        if(selectedBuildings.length != 1){
            Ext.MessageBox.alert("消息","请选择一条轮次进行查看！");
            return;
        }
        var bmlcid = selectedBuildings[0].get("BMLCID");
        this.tabPanel.setActiveTab(this.chouQuPanel);
        this.grid2.$load({"bmlcid":bmlcid});
    },
    createExamStudentCheckPanel:function(){
        //搜索区域
        var dataPanel=new Ext.Panel({
            region:'center',
            border:true,
            id:"dataPanelEI",
            html:"",
            bodyStyle:"border:2px red solid"
        });
        var print  = new Ext.Button({text:"打印",iconCls:"p-icons-print",handler:this.printExamInfo,scope:this});
        var fanhui = new Ext.Button({text:"返回",iconCls:"p-icons-unpassing",handler:this.returnExamInfo,scope:this});
        return new Ext.Panel({
            id:"MainExamRoomPanel",
            region:"north",
            tbar:[
                "->",fanhui,
                "->",print
            ],
            height:600,
            width:"auto",
            layout:"border",
            border:true,
            items:[dataPanel]
        });
    },
    /** 初始化界面 **/
    initFace:function(){
        this.addPanel(this.tabPanel);
    },
    initQueryDate:function(){
        var lcid = getLocationPram("lcid");
        this.maingrid.$load({"lcid":lcid});
    },
    selectExamInfomation:function(){
        var organ_sel = "";
        var kd_find = Ext.getCmp("kd").getValue();
        var mc_find = Ext.getCmp("mc").getValue();
        var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();
        this.examGrid.$load({"LCID":lcid,"name":xmkhxjh_sel,"SHFLAG":kd_find,"isBao":mc_find});
    },
    maingridExamStu:function(){
        // var xnxq = Ext.getCmp('infxnxq_find').getValue();
        var xnxq1 = Ext.getCmp('infxnxq_find1').getValue();
        var zp = Ext.getCmp('zp111').getValue();
        var xxkssj=Ext.getCmp('xxkssj_find').getValue();
        var xxjssj=Ext.getCmp('xxjssj_find').getValue();
        if(xxkssj!=null && xxkssj!=''){
            if(xxjssj!=null && xxjssj!=''){
                if(xxkssj.getTime()>xxjssj.getTime()){
                    Ext.MessageBox.alert("错误","起始日期大于结束日期！");
                    return;
                }
            }else {
                Ext.MessageBox.alert("错误","结束日期不能为空！");
                return;
            }
        }else {
            if(xxjssj!=null && xxjssj!=''){
                Ext.MessageBox.alert("错误","开始日期不能为空！");
                return;
            }
        }
        this.maingrid.$load({"companyName":xnxq1,"state":zp,"dateTime":xxkssj,"endTime":xxjssj});
    },
    maingridExamStu1:function() {
        var sfz = Ext.getCmp('sfz').getValue();
        var xm = Ext.getCmp('name').getValue();
        var zp = Ext.getCmp('zp').getValue();
        this.grid2.$load({"limit":this.grid2.getBottomToolbar().pageSize,"sfzjh":sfz,"xm":xm,"zp":zp});
    },
    checkExamInfomation:function(){
        //核对考生信息
        var organ_sel = "";
        if(mBspInfo.getUserType() != "2") {
            organ_sel = this.examSearch.getForm().findField('organ_sel').getCodes();
            if(organ_sel== "" || organ_sel==undefined){
                Ext.MessageBox.alert("消息","必须选择到参考单位！");
                return;
            }
        }
        var xmkhxjh_sel = Ext.getCmp('xmkhxjh_sel').getValue();
        this.tabPanel.setActiveTab(this.examCheckPanel);
        var iframe="<fieldset style='height:600; width:100%; border:0px; padding:0;'>"+
            "<iframe id='frmReportP' name='frmReportP' width='100%'"+
            "height='600' src='examInfomation_getExamInfomationByBj.do?lcid="+lcid+"&xxid="+organ_sel+
            "&xmkhxjh="+xmkhxjh_sel+"' frameborder='0' scrolling='auto' style='border:1px dashed #B3B5B4;'></iframe>"+
            "</fieldset>";
        Ext.getDom('dataPanelEI').innerHTML=iframe;
    },
    checkSfz:function(){
        var kspc_find = Ext.getCmp("kspc_find").getValue();
        var kd_find = Ext.getCmp("kd_find").getValue();
        var kc_find = Ext.getCmp("kc_find").getValue();
        var selected =  this.examGrid.getSelectionModel().getSelected();
        var delInfo = "";
        var param = "";
        if(!selected){
            if(kspc_find== "" || kspc_find==undefined){
                Ext.MessageBox.alert("消息","必须选择到考试批次！");
                return;
            }
            if(kd_find== "" || kd_find==undefined){
                Ext.MessageBox.alert("消息","必须选择到考点！");
                return;
            }
            if(kc_find== "" || kc_find==undefined){
                Ext.MessageBox.alert("消息","必须选择到考场！");
                return;
            }
            param = "pc="+kspc_find+"&kd="+kd_find+"&kc="+kc_find;
        }else {
            var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
            for(var i = 0; i < selectedBuildings.length; i++) {
                delInfo += selectedBuildings[i].get("KSCODE")+";";
            }
            param = "lcid="+lcid+"&ks="+delInfo;
        }
        window.open("index.htm?"+param);
    },
    submitJkCj:function(){
        var selected =  this.examGrid.getSelectionModel().getSelected();
        if(!selected){
            Ext.MessageBox.alert("消息","请选择一条记录！");
            return;
        }
        var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
        var thiz = this;
        var delInfo = "";
        for(var i = 0; i < selectedBuildings.length; i++) {
            delInfo += selectedBuildings[i].get("KSCODE")+";";
        }
        Ext.MessageBox.show({
            title:"消息",
            msg:"您确定要代提交成绩吗?",
            buttons:Ext.MessageBox.OKCANCEL,
            icon:Ext.MessageBox.QUESTION,
            fn:function(b){
                if(b=='ok'){
                    JH.$request({
                        timeout: 3600000,
                        params:{
                            "delInfo":delInfo,
                            "lcid":lcid
                        },
                        action:"examupdate_submitJkCj.do",
                        handler:function(){
                            thiz.examGrid.$load();
                        }
                    })
                }
            }
        })
    },
    editsfz:function(){
        var selected =  this.examGrid.getSelectionModel().getSelected();
        if(!selected){
            Ext.MessageBox.alert("消息","请选择一条记录！");
            return;
        }
        var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
        if(selectedBuildings.length>1){
            Ext.MessageBox.alert("消息","只能选择一条记录！");
            return;
        }
        var thiz = this;
        var delInfo = selectedBuildings[i].get("KSCODE");
        Ext.MessageBox.show({
            title:"消息",
            msg:"您确定要把"+delInfo+"身份证改为："+sfzjh+"吗?",
            buttons:Ext.MessageBox.OKCANCEL,
            icon:Ext.MessageBox.QUESTION,
            fn:function(b){
                if(b=='ok'){
                    JH.$request({
                        timeout: 3600000,
                        params:{
                            "delInfo":delInfo,
                            "sfzjh":sfzjh
                        },
                        action:"exam_editsfzjh.do",
                        handler:function(){
                            thiz.examGrid.$load();
                        }
                    })
                }
            }
        })
    },
    show:function(){
        var selected =  this.maingrid.getSelectionModel().getSelected();
        if(!selected){
            Ext.MessageBox.alert("消息","请选择一条记录！");
            return;
        }
        var selectedBuildings = this.maingrid.getSelectionModel().getSelections();
        var ids =selectedBuildings[0].get("ID");
        // this.lcid=ids;
        this.invoiceWindow.show(null,function(){
            this.invoiceForm.$load({
                params:{'orderNum':ids},
                action:"upload_getInvoice2.do",
                handler:function(form,result,scope){
                    form.findField("NAME").setValue(result.data.NAME);
                }

            });
        },this)
    },
    submitXk:function(){
        var selected =  this.examGrid.getSelectionModel().getSelected();
        if(!selected){
            Ext.MessageBox.alert("消息","请选择一条记录！");
            return;
        }
        var selectedBuildings = this.examGrid.getSelectionModel().getSelections();
        var thiz = this;
        var delInfo = "";
        for(var i = 0; i < selectedBuildings.length; i++) {
            delInfo += selectedBuildings[i].get("KSCODE")+";";
        }
        Ext.MessageBox.show({
            title:"消息",
            msg:"您确定要续考吗?",
            buttons:Ext.MessageBox.OKCANCEL,
            icon:Ext.MessageBox.QUESTION,
            fn:function(b){
                if(b=='ok'){
                    JH.$request({
                        timeout: 3600000,
                        params:{
                            "delInfo":delInfo,
                            "lcid":lcid
                        },
                        action:"examupdate_submitXk.do",
                        handler:function(){
                            thiz.examGrid.$load();
                        }
                    })
                }
            }
        })
    },
    fanhui:function(){
        this.tabPanel.setActiveTab(this.mainPanel);
        this.maingrid.$load();
    },
    fanhui1:function(){
        this.tabPanel.setActiveTab(this.examInfoPanel);
        this.examSearch.getForm().reset();
        this.examGrid.$load({"LCID":lcid,"name":"","isBao":"","SHFLAG":""});
    },
    printExamInfo:function(){
        frmReportP.print();
    },
    returnExamInfo:function(){
        this.tabPanel.setActiveTab(this.examInfoPanel);
        this.examGrid.$load({"examinee.lcid":lcid});
    }
});

function openPanel(PARENTID,MENUCODE){
    var tp = Ext.getCmp("treePanel_"+PARENTID);
    Ext.getCmp(tp.root.id).expand();
    tp.selectPath("/"+PARENTID+"/"+MENUCODE,"",function(){
        var node = tp.getSelectionModel().getSelectedNode();
        mainobject.clickTree(node);
    });

}
