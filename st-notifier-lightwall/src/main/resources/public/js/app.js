var stNotifierApp = angular.module('stnotifier', ['ngRoute', 'ngStomp'])
    .controller(
        'UpcomingMessageController', ['$scope', '$http', '$interval', function ($scope, $http, $interval) {
          $scope.reload = function () {
            $http.get('/message/upcoming').success(function (data) {
              $scope.messages = data;
            });
          };
          $scope.reload();
          $interval($scope.reload, 5000, 0, false);
        }
        ]
    )
    .controller(
        'LastViewedMessageController', ['$scope', '$http', '$interval', function ($scope, $http, $interval) {
          $scope.reload = function () {
            $http.get('/message/displayed').success(function (data) {
              $scope.messages = data;
            });
          };

          $scope.block = function(message) {

          };
          $scope.reload();
          $interval($scope.reload, 5000, 0, false);
        }]
    );

