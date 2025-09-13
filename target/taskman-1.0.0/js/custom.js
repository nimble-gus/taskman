/**
 * Custom JavaScript for TaskMan Application
 */

// Initialize application when DOM is ready
$(document).ready(function() {
    console.log('TaskMan application initialized');
    
    // Initialize tooltips
    initializeTooltips();
    
    // Initialize form validations
    initializeFormValidations();
    
    // Initialize AJAX loading indicators
    initializeAjaxLoading();
});

/**
 * Initialize tooltips for better UX
 */
function initializeTooltips() {
    // Add tooltips to buttons with title attributes
    $('[title]').tooltip();
    
    // Add tooltips to status badges
    $('.status-badge, .priority-badge').each(function() {
        var $this = $(this);
        if (!$this.attr('title')) {
            $this.attr('title', $this.text());
        }
    });
}

/**
 * Initialize form validations
 */
function initializeFormValidations() {
    // Add real-time validation feedback
    $('input[required], textarea[required]').on('blur', function() {
        validateField($(this));
    });
    
    // Add form submission validation
    $('form').on('submit', function(e) {
        var isValid = true;
        $(this).find('input[required], textarea[required]').each(function() {
            if (!validateField($(this))) {
                isValid = false;
            }
        });
        
        if (!isValid) {
            e.preventDefault();
            showMessage('Por favor, complete todos los campos obligatorios', 'error');
        }
    });
}

/**
 * Validate individual field
 */
function validateField($field) {
    var value = $field.val();
    var isValid = value && value.trim().length > 0;
    
    if (isValid) {
        $field.removeClass('ui-state-error');
        $field.addClass('ui-state-valid');
    } else {
        $field.removeClass('ui-state-valid');
        $field.addClass('ui-state-error');
    }
    
    return isValid;
}

/**
 * Initialize AJAX loading indicators
 */
function initializeAjaxLoading() {
    // Show loading indicator on AJAX requests
    $(document).ajaxStart(function() {
        showLoadingIndicator();
    });
    
    $(document).ajaxStop(function() {
        hideLoadingIndicator();
    });
}

/**
 * Show loading indicator
 */
function showLoadingIndicator() {
    if ($('#loadingIndicator').length === 0) {
        $('body').append('<div id="loadingIndicator" class="loading-indicator"><i class="fa fa-spinner fa-spin"></i> Cargando...</div>');
    }
    $('#loadingIndicator').show();
}

/**
 * Hide loading indicator
 */
function hideLoadingIndicator() {
    $('#loadingIndicator').hide();
}

/**
 * Show custom message
 */
function showMessage(message, type) {
    type = type || 'info';
    
    var alertClass = 'alert-info';
    switch(type) {
        case 'success':
            alertClass = 'alert-success';
            break;
        case 'error':
            alertClass = 'alert-danger';
            break;
        case 'warning':
            alertClass = 'alert-warning';
            break;
    }
    
    var $message = $('<div class="alert ' + alertClass + ' alert-dismissible fade in" role="alert">' +
                    '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
                    '<span aria-hidden="true">&times;</span>' +
                    '</button>' +
                    message +
                    '</div>');
    
    $('body').prepend($message);
    
    // Auto-dismiss after 5 seconds
    setTimeout(function() {
        $message.alert('close');
    }, 5000);
}

/**
 * Format date for display
 */
function formatDate(dateString) {
    if (!dateString) return '';
    
    var date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
}

/**
 * Format datetime for display
 */
function formatDateTime(dateString) {
    if (!dateString) return '';
    
    var date = new Date(dateString);
    return date.toLocaleString('es-ES', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
    });
}

/**
 * Check if date is overdue
 */
function isOverdue(dueDate) {
    if (!dueDate) return false;
    
    var today = new Date();
    var due = new Date(dueDate);
    
    return due < today;
}

/**
 * Toggle task completion with animation
 */
function toggleTaskCompletion(taskId, isCompleted) {
    var $row = $('tr[data-task-id="' + taskId + '"]');
    
    if (isCompleted) {
        $row.addClass('task-completed');
        $row.find('.task-title').css('text-decoration', 'line-through');
    } else {
        $row.removeClass('task-completed');
        $row.find('.task-title').css('text-decoration', 'none');
    }
}

/**
 * Confirm dialog with custom message
 */
function confirmAction(message, callback) {
    if (confirm(message)) {
        if (typeof callback === 'function') {
            callback();
        }
        return true;
    }
    return false;
}

/**
 * Initialize data table enhancements
 */
function initializeDataTables() {
    // Add row hover effects
    $('.ui-datatable tbody tr').hover(
        function() {
            $(this).addClass('ui-state-hover');
        },
        function() {
            $(this).removeClass('ui-state-hover');
        }
    );
    
    // Add click effects for action buttons
    $('.ui-button').on('click', function() {
        $(this).addClass('ui-state-active');
        setTimeout(function() {
            $('.ui-button').removeClass('ui-state-active');
        }, 200);
    });
}

/**
 * Initialize responsive behavior
 */
function initializeResponsive() {
    // Handle window resize
    $(window).on('resize', function() {
        adjustLayoutForScreenSize();
    });
    
    // Initial adjustment
    adjustLayoutForScreenSize();
}

/**
 * Adjust layout based on screen size
 */
function adjustLayoutForScreenSize() {
    var width = $(window).width();
    
    if (width < 768) {
        // Mobile adjustments
        $('.stats-content').addClass('mobile-layout');
        $('.quick-actions').addClass('mobile-layout');
        $('.task-stats').addClass('mobile-layout');
    } else {
        // Desktop layout
        $('.stats-content').removeClass('mobile-layout');
        $('.quick-actions').removeClass('mobile-layout');
        $('.task-stats').removeClass('mobile-layout');
    }
}

// Initialize responsive behavior
$(document).ready(function() {
    initializeResponsive();
    initializeDataTables();
});
