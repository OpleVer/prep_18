(function() {
    'use strict';
    angular
        .module('prep18App')
        .factory('Actas', Actas);

    Actas.$inject = ['$resource'];

    function Actas ($resource) {
        var resourceUrl =  'api/actas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
