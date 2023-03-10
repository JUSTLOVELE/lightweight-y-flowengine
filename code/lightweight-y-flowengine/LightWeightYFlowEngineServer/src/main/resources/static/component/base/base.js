var baseCode = `
<div>
    <el-form ref='base' size="small" :inline="true" class="query-from" @submit.native.prevent>
        <div style="background-color: #f2f2f2;padding-top: 5px" ref="formHeader">
            <el-form-item v-if="form.buttons && form.buttons.length > 0">
                <el-button :disabled="typeof item.dynamic === \'function\' ? !item.dynamic(currentRow, queryParams) : false"
                            v-for="(item,index) in form.buttons" 
                            :key="item.name"
                            size="mini" 
                            :type="getBtnType(item.type)" 
                            :icon="getBtnIcon(item.type)" 
                            @click="btnClickHandler(item)">{{item.label}}</el-button>
            </el-form-item>
            <template v-if="form.uploadButtons && form.uploadButtons.length > 0">
                <el-form-item  v-for="(item,index) in form.uploadButtons" :key="item.name">
                    <el-upload  :show-file-list=false
                                :ref="'formUploadButtonRef' + index"
                                :accept="item.accept"
                                :action="item.action"
                                :name="item.name"
                                :before-upload="(file) => { return handlerUploadButtonBeforeUpload(file,index, item)}"
                                :on-success="(response, file, fileList) => { return handlerUploadButtonOnSuccess(response, file, fileList, index, item)}"
                                :data="uploadButtonParam"
                                :limit="item.size">
                        <el-button :disabled="typeof item.dynamic === \'function\' ? !item.dynamic(currentRow, queryParams) : false" 
                                    size="mini" 
                                    :type="getBtnType(item.type)" 
                                    :icon="getBtnIcon(item.type)">
                                    {{item.label}}</el-button>
                    </el-upload>
                </el-form-item>
            </template>
            <template v-if="form.inputs && form.inputs.length > 0">
                <el-form-item v-for="(item,index) in form.inputs" :key="item.name">
                    <el-input :placeholder="item.placeholder" 
                              prefix-icon="el-icon-search" 
                              v-model.trim="queryParams[item.name]" 
                              clearable></el-input>
                </el-form-item>
            </template>
            <template v-if="form.radios && form.radios.length > 0">
                <el-form-item v-for="item in form.radios" :key="item.name">
                    <el-radio-group @change="typeof item.onChange === \'function\' ? item.onChange($event, queryParams, currentRow) : function(){}" 
                            v-model="queryParams[item.name]">
                        <el-radio-button v-for="opt in item.options" :label="opt.value">{{opt.label}}</el-radio-button>
                    </el-radio-group>
                </el-form-item>
            </template>
            <template v-if="form.selects && form.selects.length > 0">
                <el-form-item v-for="item in form.selects" :key="item.name">
                    <el-select :ref="item.id"
                         @change="typeof item.onChange === \'function\' ? item.onChange($event, queryParams, currentRow) : function(){}" 
                         :placeholder="item.placeholder" 
                         v-bind="item.props" 
                         v-model="queryParams[item.name]" 
                         :clearable="item.clearable">
                             <el-option v-for="subItem in item.options"
                                        :key="subItem.value"
                                        :label="subItem.label"
                                        :value="subItem.value">
                             </el-option>
                        </el-select>
                </el-form-item>
            </template>
            <template v-if="form.dates && form.dates.length > 0">
                <el-form-item v-for="(item,idx) in form.dates" :key="idx">
                    <el-date-picker :type="item.type" 
                                    v-model="queryParams[item.name]" 
                                    range-separator="???" 
                                    :placeholder="item.placeholder || \'????????????\'" 
                                    :start-placeholder="item.startPlaceholder || \'????????????\'" 
                                    :end-placeholder="item.endPlaceholder || \'????????????\'" 
                                    value-format="yyyy-MM-dd HH:mm:ss" 
                                    unlink-panels clearable>
                    </el-date-picker>
                </el-form-item>
            </template>
            <el-button size="mini" icon="el-icon-search" @click="conditionFormQuery" style="margin-bottom: 12px;height: 32px;">??????</el-button>
        </div>
    </el-form>
    <el-row>
        <el-col :span="table.layout ? table.layout.left : 24">
            <div class="left-content">
                <table-component ref="globalSingleTable"
                                 :cols="table.cols"
                                 :url="table.url"
                                 :enum-map="table.enumMap"
                                 :sort="table.sort"
                                 :params="table.params"
                                 :query-obj="queryParams"
                                 :row-class-name="table.tableRowClassName"
                                 :data-process="table.dataProcess"
                                 :first-selected="table.firstSelected"
                                 :page-sizes="table.pageSizes"
                                 :manual="table.manual"
                                 :handler="table.handler"
                                 @current-row-change="currentRowChange"
                                 @multi-row-change="multiRowChange"
                                 @row-click="rowClick"
                                 @get-form-header-height="getFormHeaderHeight"
                                 @update="onTableUpdate">
                    <template slot="tableColumn">
                        <slot name="tableColumnSlot"></slot>
                    </template>
                </table-component>
            </div>
        </el-col>
        <el-col :span="table.layout ? table.layout.right : 0" style="border-bottom: 1px solid #EBEEF5;">
            <div class="right-content">
                <slot name="tableRightContainer"></slot>
            </div>
        </el-col>
    </el-row>
    <template v-for="item in dialogs">
        <form-dialog-component v-if="item.requireRow" 
                                    :ref="item.ref" 
                                    :width="item.width" 
                                    :readonly="item.readonly" 
                                    :no-footer="false" 
                                    :title="item.title" 
                                    :buttons="item.buttons"
                                    :items="item.items" 
                                    :event="item.event"
                                    :module="item.module" 
                                    :current-row="currentRow" 
                                    :form-label-width="item.formLabelWidth" 
                                    @itemEvent="onItemEvent(arguments, item.onItemEvent)"
                                    @show="onDialogShow(arguments, item.onShow)"
                                    @close="onDialogClose(item.onClose)"
                                    @valid="onDialogValid(arguments, item.ref, item.submitUrl, item.handler, item.manual, item.params, item.paramFields, item.module)" 
                                   >
            <template slot="middle">
                <slot :name="item.ref + \'Slot\'"></slot>
            </template>
        </form-dialog-component>
        <form-dialog-component v-else :ref="item.ref" 
                                      :width="item.width" 
                                      :readonly="item.readonly" 
                                      :no-footer="false" 
                                      :title="item.title" 
                                      :buttons="item.buttons"
                                      :items="item.items" 
                                      :event="item.event"
                                      :module="item.module" 
                                      :subForm="item.subForm" 
                                      :form-label-width="item.formLabelWidth" 
                                      @itemEvent="onItemEvent(arguments, item.onItemEvent)" 
                                      @show="onDialogShow(arguments, item.onShow)" 
                                      @close="onDialogClose(item.onClose)"
                                      @valid="onDialogValid(arguments, item.ref, item.submitUrl, item.handler, item.manual, item.params, item.paramFields, item.module)" 
                                     >
            <template slot="middle">
                <slot :name="item.ref + \'Slot\'"></slot>
            </template>
        </form-dialog-component>
    </template>
</div>

`;

Vue.component('base-component', {
    template: baseCode,
    props: {
        form: {
            type: Object,
            default: function () {
                return {
                    /*
                    {operation: '', type: '', icon: '', click: function () {}, bindRef: '', label: ''}
                    */
                    buttons: [],
                    /*
                    {name: '', placeholder: ''}
                    */
                    inputs: [],
                    /*
                    {name: '', placeholder: '', options: []}
                    */
                    selects: [],
                    /*
                    {name: '', type, placeholder: '', startPlaceholder: '', endPlaceholder}
                    */
                    dates: [],
                    /*
                    {name: '', options: [{label: '', value: ''}, ...]}
                    */
                    radios: [],
                }
            }
        },
        table: {
            type: Object,
            default: function () {
                return {
                    cols: [],
                    url: '',
                    enumMap: {},
                    sort: '',
                    params: {},
                    dataProcess: function() {},
                    firstSelected: false, //?????????????????????
                    layout: { //?????????????????????????????????????????????????????????
                        left: 24,
                        right: 0
                    },
                    pageSizes: [10, 25, 50, 100]
                }
            }
        },
        dialogs: {
            type: Array,
            default: function () {
                return []
            }
        }
    },
    created: function() {
        this.initHeaderCombox()
    },
    mounted: function () {

    },
    data: function() {
        var _this = this
        var initQueryParams = {}
        //??????????????????
        Object.keys(_this.form).forEach(function (key) {
            if(key !== 'buttons' && key !=='uploadButton') {
                _this.form[key].forEach(function (item) {
                    if(item.value != null) { //????????????
                        initQueryParams[item.name] = item.value
                    } else {
                        initQueryParams[item.name] = '' //??????????????????
                    }
                })
            }
        })
        return {
            queryParams: initQueryParams,
            currentRow: null,
            multiRow: null,
            uploadButtonParam: {},
            selectCommbox: {
            }
        }
    },
    watch: {
        'queryParams': {
            handler(newValue) {
                //????????????????????????
                handlerDateRange(this.queryParams)
                this.$emit("query-param", this.queryParams)
            },
            deep: true,
            immediate: true
        }
    },
    methods: {
        onDialogValid: function (arguments, ref, url, handler, manual, params, paramFields, module) {

            var vm = this
            var formData = arguments[0]
            var rawData = arguments[1]
            var cb = arguments[2]

            if(module && module.uploader && formData.has("submitUrl")) {
                //?????????????????????
                url = formData.get("submitUrl")
                formData.delete("submitUrl")
            }else if(formData['submitUrl']) {
                url = formData['submitUrl']
                delete formData['submitUrl']
            }

            if (manual) { //??????
                handler(vm, formData, rawData, cb, ref, url)
                return
            }

            if (formData.constructor === FormData) {

                if (rawData != null && Object.prototype.toString.call(paramFields) === "[object Array]") { //??????paramFields???????????????key
                    paramFields.forEach(function (field) {
                        if (formData.get(field) == null && rawData[field] != null) {
                            formData.set(field, rawData[field])
                        }
                    })
                }

                if (params != null && typeof params === 'object') {
                    if (Object.prototype.toString.call(params) === "[object Array]") {
                        params.forEach(function (item) {
                            Object.keys(item).forEach(function (key) {
                                formData.set(key, item[key])
                                //console.log(key +";"+ item[key])
                            })
                        })
                    } else {
                        Object.keys(params).forEach(function (key) {
                            formData.set(key, params[key])
                        })
                    }
                }

                $.ajax({
                    method: "POST",
                    url: url,
                    data: formData,
                    processData: false,
                    contentType: false,
                }).done(function (resData) {
                    // var resData = JSON.parse(res)
                    if (!(resData.code === 100 || resData.success)) {
                        vm.$message.error(resData.desc || '????????????');
                        cb()
                        return
                    }
                    if (typeof handler === 'function' && handler(resData) === false) {
                        return
                    }
                    vm.$message.success("????????????");
                    vm.refreshTable()
                    vm.$refs[ref][0].hide();
                    cb()
                }).fail(function (res) {
                    vm.$message.error("????????????");
                    cb()
                })
            }else{

                if (rawData != null && Object.prototype.toString.call(paramFields) === "[object Array]") { //??????paramFields???????????????key
                    paramFields.forEach(function (field) {
                        if (formData[field] == null && rawData[field] != null) {
                            formData[field] = rawData[field]
                        }
                    })
                }

                if (params != null && typeof params === 'object') {
                    if (Object.prototype.toString.call(params) === "[object Array]") {
                        params.forEach(function (item) {
                            Object.keys(item).forEach(function (key) {
                                formData[key] = Object.prototype.toString.call(item[key]) === "[object Array]" ? JSON.stringify(item[key]) : item[key]
                            })
                        })

                    } else {
                        Object.keys(params).forEach(function (key) {
                            formData[key] = Object.prototype.toString.call(params[key]) === "[object Array]" ? JSON.stringify(params[key]) : params[key]
                        })
                    }
                }

                $.ajax({
                    method: "POST",
                    url: url,
                    data: formData,
                }).done(function (resData) {
                    // var resData = JSON.parse(res)
                    console.log(resData)
                    if (!(resData.code === 100 || resData.success)) {
                        vm.$message.error(resData.desc || '????????????');
                        cb()
                        return
                    }
                    if (typeof handler === 'function' && handler(resData) === false) {
                        return
                    }
                    vm.$message.success("????????????");
                    vm.refreshTable()
                    vm.$refs[ref][0].hide();
                    cb()
                }).fail(function (res) {
                    vm.$message.error("????????????");
                    cb()
                })
            }
        },
        onDialogShow: function (arguments, fn) {
            //console.log(arguments)
            if (typeof fn === 'function') {
                fn.call(this, arguments[0])
            }
        },
        onDialogClose: function (fn) {
            if (typeof fn === 'function') {
                fn.call(this)
            }
        },
        onItemEvent: function (arguments, fn) {

            var key = arguments[0];
            var json = {
                'field': arguments[1],
                'value': arguments[2]
            }

            if(key === 'change') {
                this.$emit("combox-select-change", json)
            }

            if (typeof fn === 'function') {
                fn.apply(null, arguments)
            }
        },
        initHeaderCombox: function () {
            var _this = this;

            if(_this.form.selects && _this.form.selects.length > 0) {

                _this.form.selects.forEach(function(item){

                    if(item.optionsUrl) {
                        $.ajax({
                            method: "GET",
                            url: item.optionsUrl,
                            data: {},
                            async:false,//????????????
                        }).done(function (resData) {
                            // var resData = JSON.parse(res)
                            if(item.isRemoteSelected) {

                                _this.selectCommbox[item.id] = resData.value;
                                _this.queryParams[item.name] = resData.value;
                                resData = resData.datas;
                            }

                            item.options = resData.map(function (subItem) {
                                if (!!Number(subItem.value) || Number(subItem.value) === 0) { //??????NaN???,?????????number??????
                                    subItem.value = String(subItem.value)
                                }
                                return {label: subItem.name || subItem.text, value: subItem.value}
                            })
                        });

                    }
                });
            }
        },
        getSelectedCombox:function (refValue) {
            var component = this.$refs[refValue][0];
            component.value = this.selectCommbox[refValue];
            delete this.selectCommbox[refValue]
        },
        handlerUploadButtonOnSuccess: function(response, file, fileList, index, item) {

            let vm = this
            if(item.onSuccess) {
                item.onSuccess.call()
            }

            if(item.onCall) {
                vm.$emit(item.onCall, response, file, fileList, index)
            }else{
                
                if (!(response.code === 100 || response.success)) {

                    if(response.code === 102) {
                        vm.$message.error(response.desc);
                    }else{
                        vm.$message.error('????????????');
                    }

                    return
                }

                vm.$message.success("????????????");
                let ref = 'formUploadButtonRef' + index
                vm.$refs[ref].clearFiles();
                vm.refreshTable();
            }
        },
        handlerUploadButtonBeforeUpload: function(file, index, item) {

            let vm = this

            if(item.required && !this.currentRow) {
                //?????????????????????????????????????????????
                vm.$message.error("?????????????????????");
                return false
            }

            if(item.fields && item.fields.length > 0 && !this.currentRow) {
                //??????????????????,??????????????????????????????
                vm.$message.error("?????????????????????");
                return false
            }

            item.fields.forEach(function (field) {
                vm.uploadButtonParam[field] = vm.currentRow[field]
            })

            return true
        },
        getFormHeaderHeight: function(obj) {
            //tab?????????-????????????-??????????????????(???????????????45) ?????? ??????table?????????
            let height = window.innerHeight - this.$refs['formHeader'].clientHeight - 45;
            height = height > 60 ? height : 60
            this.$refs['globalSingleTable'].TheStyle["min-height"] = height + "px"
        },
        onTableUpdate: function(data) { //data: {rows: [], total: 0}
            this.$emit("table-update", data)
        },
        currentRowChange: function (val) {
            //??????base.js??????@current-row-change???????????????
            this.currentRow = val
            this.$emit("current-row-change", val)
        },
        rowClick: function(row) {
            this.$emit("row-click", row)
        },
        multiRowChange: function (array) {
            this.multiRow = array
            this.$emit("multi-row-change", array)
        },
        conditionFormQuery: function() {
            this.$refs['globalSingleTable'].getData()
        },
        btnClickHandler: function(item) {

            let multi = item.multi ? item.multi : false

            if(item.type === DELETE) {
                this['deleteOrNotice'](item.url, item.fields, item.message, item.cancel, WARNING, multi)
                return
            }

            if(item.type === NOTICE) {
                this['deleteOrNotice'](item.url, item.fields, item.message, item.cancel, WARNING, multi)
                return
            }

            if(typeof item.click === 'function') {
                item.click(this)
                return
            }

            let ref = item.bindRef

            if (this.$refs[ref] && typeof this.$refs[ref][0].show === 'function') {
                this.$refs[ref][0].show()
            }
        },
        showFormDialog: function(ref, row) {
            this.$refs[ref][0].setCurrentRow(row);
            this.$refs[ref][0].show()
        },
        deleteOrNotice: function(url, fields, message, type, cancel, multi) {

            var vm = this
            var data = {}
            //multi???true?????????multiRow????????????????????????
            if(multi) {

                if((!vm.multiRow || (vm.multiRow && vm.multiRow.length == 0))) {
                    vm.$message.error("?????????????????????");
                    return
                }

            }else if (!vm.currentRow) {
                vm.$message.error("?????????????????????");
                return
            }

            fields.forEach(function (field) {

                if(multi) {

                    var array = []

                    for(var i=0; i<vm.multiRow.length; i++) {
                        array.push(vm.multiRow[i][field])
                    }

                    data[field] = array

                }else{
                    data[field] = vm.currentRow[field]
                }
            })

            console.log(Object.assign(data))

            if(!message) {
                message = '?????????????????????????????????, ?????????????';
            }

            this.$confirm(message, '??????', {
                confirmButtonText: '??????',
                cancelButtonText: '??????',
                type: type
            }).then(function () { //confirm
                $.ajax({
                    method: "get",
                    traditional:true,
                    url: url,
                    data: Object.assign(data),
                }).done(function (resData) {
                    // var resData = JSON.parse(res)
                    if (!(resData.code === SUUCESS_CODE || resData.success)) {

                        if(resData.code === PRINT_CODE) {
                            vm.$message.error(resData.desc);
                        }else{
                            vm.$message.error('????????????');
                        }

                        return
                    }

                    vm.$message.success("????????????");
                    vm.refreshTable()
                }).fail(function (res) {
                    vm.$message.error("????????????");
                })
            }).catch(function () { //cancel
                typeof cancel === 'function' ? cancel() : null
            });
        },
        refreshTable: function () {
            this.$refs['globalSingleTable'].refresh()
        },
        getComp: function (ref) {
            return Object.prototype.toString.call(this.$refs[ref]) === '[object Array]' ? this.$refs[ref][0] : this.$refs[ref]
        },
        getBtnType: function (type) {

            switch (type) {
                case 'add':
                    return 'success'
                case 'view':
                    return ''
                case 'edit':
                    return 'primary'
                case 'delete':
                    return 'danger'
                case 'submit':
                    return 'primary'
                case 'notice':
                    return 'warning'
            }
        },
        getBtnIcon: function (type) {
            switch (type) {
                case 'add':
                    return 'el-icon-circle-plus-outline'
                case 'edit':
                    return 'el-icon-edit'
                case 'view':
                    return 'el-icon-view'
                case 'delete':
                    return 'el-icon-delete'
                case 'download':
                    return 'el-icon-download'
                case 'submit':
                    return 'el-icon-finished'
                case 'notice':
                    return 'el-icon-star-off'
            }
        },
        setDialogComponentValue: function(param) {
            this.$refs[param.ref][0].setComponentValue(param);
        },
        getDialogFormData: function (formDialogName) {
            return this.$refs[formDialogName][0].getFormData();
        },
        getQueryParam: function () {
            return this.queryParams;
        },
        setFormDialogTableData(ref, key) {
            this.$refs[ref][0].setFormDialogTableData(key);
        }
    }

});