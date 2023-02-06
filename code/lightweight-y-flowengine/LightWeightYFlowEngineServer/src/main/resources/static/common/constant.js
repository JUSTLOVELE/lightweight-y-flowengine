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

// var _SERVER = 'http://localhost:8080'
var _SERVER = "http://172.16.230.153:8080"

var GLOBAL_API = {
    ADMIN: {
        USER_QUERY: _SERVER + '/srm/userAction/query',
        USER_ADD: _SERVER + '/srm/userAction/add',
        USER_DELETE: _SERVER + '/srm/userAction/delete',
        USER_EDIT: _SERVER + '/srm/userAction/edit',
        GET_USER_COMBOX: _SERVER + '/srm/userAction/getUserCombox',
        NOTICE: _SERVER + '/srm/userAction/queryToDeal'
    },
    ORG: {
        COMBOX: _SERVER + '/srm/orgAction/getCombox',
    },
    ROLE: {
        QUERY: _SERVER + '/srm/roleAction/query',
        ADD: _SERVER + '/srm/roleAction/add',
        EDIT: _SERVER + '/srm/roleAction/edit',
        DELETE: _SERVER + '/srm/roleAction/delete',
        COMBOX: _SERVER + '/srm/roleAction/getCombox'
    },
    TREE: {
        ROLE: _SERVER + '/srm/roleAction/createRoleTree'
    },
    POP_ACTIVE: {
        QUERY: _SERVER + '/srm/popActiveAction/query',
        PUBLISH: _SERVER + '/srm/popActiveAction/publishPopActiveAction',
        EDIT: _SERVER + '/srm/popActiveAction/editPopActiveAction',
        AUDITING: _SERVER + '/popActiveAction/auditingPopActiveAction',
        DELETE: _SERVER + '/srm/popActiveAction/deletePopActiveAction',
        BACK: _SERVER + '/srm/popActiveAction/back',
        PASS: _SERVER + '/srm/popActiveAction/pass',
    },
    IND_POP_ACTIVE: {
        QUERY: _SERVER + '/srm/indPopActiveAction/query',
        ENTER: _SERVER + '/srm/indPopActiveAction/enter',
    },
    PRODUCT: {
        GET_PRODUCT_TYPE_COMBOX: _SERVER + '/srm/productWriteAction/getProductTypeCombox',
        QUERY: _SERVER + '/srm/productWriteAction/query',
        SUBMIT: _SERVER + '/srm/productWriteAction/addProduct',
        DELETE:  _SERVER + '/srm/productWriteAction/delete',
        EDIT: _SERVER + '/srm/productWriteAction/editProduct',
        MANAGE_PRODUCT: _SERVER + '/srm/productWriteAction/manageQuery',
        BACK: _SERVER + '/srm/productWriteAction/back',
        PASS: _SERVER + '/srm/productWriteAction/pass',
    },
    AWARDS: {
        QUERY: _SERVER + '/srm/indAwardsAction/query',
        SUBMIT: _SERVER + '/srm/indAwardsAction/addAwards',
        DELETE:  _SERVER + '/srm/indAwardsAction/delete',
        EDIT: _SERVER + '/srm/indAwardsAction/editAwards',
        MANAGE_PRODUCT: _SERVER + '/srm/indAwardsAction/manageQuery',
        BACK: _SERVER + '/srm/indAwardsAction/back',
        PASS: _SERVER + '/srm/indAwardsAction/pass',
    },
    BOOK: {
        QUERY: _SERVER + '/srm/indBookAction/query',
        SUBMIT: _SERVER + '/srm/indBookAction/addBook',
        DELETE:  _SERVER + '/srm/indBookAction/delete',
        EDIT: _SERVER + '/srm/indBookAction/editBook',
        MANAGE_BOOK: _SERVER + '/srm/indBookAction/manageQuery',
        BACK: _SERVER + '/srm/indBookAction/back',
        PASS: _SERVER + '/srm/indBookAction/pass',
    },
    NOTICE: {
        SUBMIT: _SERVER + '/srm/noticeAction/add',
        QUERY: _SERVER + '/srm/noticeAction/query',
        DELETE: _SERVER + '/srm/noticeAction/delete',
        EDIT: _SERVER + '/srm/noticeAction/edit',
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