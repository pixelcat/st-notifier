var stNotifierApp = angular.module('stnotifier', ['ngRoute'])
    .controller('TabController', ['$scope', function ($scope) {
      $scope.tabs = [
        {
          title: 'Messages',
          url: 'messages.html'
        },
        {
          title: 'Config',
          url: 'config.html'
        }
      ];
      $scope.currentTab = 'messages.html';

      $scope.onClickTab = function (tab) {
        $scope.currentTab = tab.url;
      }

      $scope.isActiveTab = function (tabUrl) {
        return tabUrl == $scope.currentTab;
      }
    }])
    .controller(
        'ConfigController', ['$scope', '$http', function ($scope, $http) {
          $http.get('/st-config').success(
              function (data) {
                $scope.config = data;
              });

          $scope.master = {};

          $scope.update = function (config) {
            $http.post('/st-config', config).success(function (data) {
              $scope.master = angular.copy(data);
            })
          };

          $scope.reset = function () {
            $scope.config = angular.copy($scope.master);
          };

          $scope.reset();
        }]
    )
    .controller(
        'UpcomingMessageController', ['$scope', '$http', '$interval', '$rootScope', function ($scope, $http, $interval, $rootScope) {

          // get paused state from server
          $http.get('/st-listener').success(
              function (data) {
                $scope.paused = data;
              });

          $scope.pauseMessages = function () {
            $http.get('/st-listener/true').success(
                function (data) {
                  $scope.paused = true;
                });
          };

          $scope.resumeMessages = function () {
            $http.get('/st-listener/false').success(
                function (data) {
                  $scope.paused = false;
                }
            )
          };

          $scope.reload = function () {
            $http.get('/message/upcoming').success(
                function (data) {
                  $scope.messages = data;
                });
          };

          $scope.markSticky = function (message) {
            $http.get('/message/marksticky/' + message.id + '/true').success(
                function (data) {
                  $rootScope.$emit('ReloadEvent', {});
                });
          };

          $scope.unmarkSticky = function (message) {
            $http.get('/message/marksticky/' + message.id + '/false').success(
                function (data) {
                  $rootScope.$emit('ReloadEvent', {});
                });
          };

          $scope.block = function (message) {
            $http.get('/message/block/' + message.id).success(
                function (data) {
                  $rootScope.$emit('ReloadEvent', {});
                }
            )
          };

          $scope.unblock = function (message) {
            $http.get('/message/unblock/' + message.id).success(
                function (data) {
                  $rootScope.$emit('ReloadEvent', {});
                }
            )
          };
          $rootScope.$on('ReloadEvent', function () {
            $scope.reload();
          });

          $scope.reload();

          //  $interval($scope.reload, 5000, 0, false);
        }
        ]
    )
    .controller(
        'LastViewedMessageController', ['$scope', '$http', '$interval', '$rootScope', function ($scope, $http, $interval, $rootScope) {
          $scope.reload = function () {
            $http.get('/message/displayed').success(
                function (data) {
                  $scope.messages = data;
                });
          };

          $rootScope.$on('ReloadEvent', function () {
            $scope.reload();
          });

          $scope.reload();
          $interval($scope.reload, 5000, 0, false);
        }]
    );

