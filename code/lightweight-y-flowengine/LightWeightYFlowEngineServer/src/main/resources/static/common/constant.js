const DELETE = "delete"
const NOTICE = "notice"
const SUUCESS_CODE = 100
const ERROR_CODE = 101
const PRINT_CODE = 102
const WARNING = 'warning'
const START_TIME = 'startTime'
const END_TIME = 'endTime'
const CONVERT = 'convert'
const CONVERT_TEXT = 'convertText'

var _SERVER = 'http://localhost:8080'

var GLOBAL_API = {
    ADMIN: {
        USER_QUERY: _SERVER + '/flow/userAction/query',
        USER_ADD: _SERVER + '/flow/userAction/add',
        USER_DELETE: _SERVER + '/flow/userAction/delete',
        USER_EDIT: _SERVER + '/flow/userAction/edit',
        GET_USER_COMBOX: _SERVER + '/flow/userAction/getUserCombox',
        NOTICE: _SERVER + '/flow/userAction/queryToDeal'
    },
    ROLE: {
        QUERY: _SERVER + '/flow/roleAction/query',
        ADD: _SERVER + '/flow/roleAction/add',
        EDIT: _SERVER + '/flow/roleAction/edit',
        DELETE: _SERVER + '/flow/roleAction/delete',
        COMBOX: _SERVER + '/flow/roleAction/getCombox'
    },
    TREE: {
        ROLE: _SERVER + '/flow/roleAction/createRoleTree'
    },
    FlowManage: {
        QUERY: _SERVER + '/flow/mainAction/query',
    },
    Dept: {
        SELECT_QUERY: _SERVER + '/flow/deptAction/selectQuery',
    }
}

var GLOBAL_ENUM = {
    STATUS: {
        CONVERT_TEXT: 'statusText',
        CONVERT: {
            '1': '发布',
            '2': '待审核',
            '3': '撤销'
        }
    },
    SEX: {
        CONVERT_TEXT: 'sexText',
        CONVERT: {
            '1': '男',
            '2': '女',
        }
    }
}